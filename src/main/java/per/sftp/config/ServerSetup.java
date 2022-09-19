package per.sftp.config;

import per.sftp.listener.LocalSftpEventListener;
import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.common.keyprovider.MappedKeyPairProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@Startup
public class ServerSetup {

    @Inject
    @ConfigProperty(name = "sftp_server_port")
    Provider<Integer> sftpServerPort;
    private Integer sftpServerPortLocal;

    @Inject
    @ConfigProperty(name = "sftp_server_host")
    Provider<String> sftpServerHost;
    private String sftpServerHostLocal;

    @Inject
    @ConfigProperty(name = "server_cert_path")
    Provider<String> sftpCertPath;
    private String serverCertPathLocal;

    @Inject
    @ConfigProperty(name = "server_cert_password")
    Provider<String> sftpCertPassword;
    private String serverCertPasswordLocal;

    @Inject
    ManagedExecutor mes;

    @Inject
    PasswordAuth passwordAuth;

    @Inject
    AccountFileSystem accountFileSystem;

    @Inject
    LocalSftpEventListener localSftpEventListener;

    SshServer sshd;

    @PostConstruct
    public void init() {
        sftpServerPortLocal = sftpServerPort.get();
        sftpServerHostLocal = sftpServerHost.get();
        serverCertPathLocal = sftpCertPath.get();
        serverCertPasswordLocal = sftpCertPassword.get();

        log.info("Starting SFTP Server");
        sshd = SshServer.setUpDefaultServer();
        sshd.setPort(sftpServerPortLocal);
        sshd.setHost(sftpServerHostLocal);
        SftpSubsystemFactory factory = new SftpSubsystemFactory();
        factory.addSftpEventListener(localSftpEventListener);
        sshd.setSubsystemFactories(Collections.singletonList(factory));
        sshd.setPasswordAuthenticator(passwordAuth);
        sshd.setFileSystemFactory(accountFileSystem);
        mes.submit(() -> {
            try {
                sshd.setKeyPairProvider(new MappedKeyPairProvider(getPrivateKey()));
                sshd.start();
                log.info("Started SFTP Server");
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @PreDestroy
    public void destroy() {
        if (sshd != null) {
            try {
                sshd.close();
                log.info("Closed SFTP Server");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private KeyPair getPrivateKey() {
        try (FileInputStream fileInputStream = new FileInputStream(serverCertPathLocal)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fileInputStream, serverCertPasswordLocal.toCharArray());
            String alias = keyStore.aliases().nextElement();
            java.security.cert.Certificate certificate = keyStore.getCertificate(alias);
            return new KeyPair(certificate.getPublicKey(), (PrivateKey) keyStore.getKey(alias, serverCertPasswordLocal.toCharArray()));
        } catch (Throwable e) {
            log.error("Error occurred while extracting certificate", e);
            throw new RuntimeException("Unable to load key file");
        }
    }

}
