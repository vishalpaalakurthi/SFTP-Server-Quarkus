package per.sftp.config;

import per.sftp.model.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.session.SessionContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Provider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Dependent
@Slf4j
public class AccountFileSystem extends VirtualFileSystemFactory implements FileSystemFactory  {

    @Inject
    @ConfigProperty(name = "directory_path")
    Provider<String> directoryPath;
    String directoryPathLocal;

    @PostConstruct
    public void init() {
        directoryPathLocal = directoryPath.get();
    }

    @Override
    public Path getUserHomeDir(SessionContext session) throws IOException {
        File file = new File(getDefaultRootDir(session));
        Files.createDirectories(file.toPath());
        log.info("directory created {}", file.toPath());
        return file.toPath();
    }

    private String getDefaultRootDir(SessionContext session) {
        String username = session.getAttribute(Attributes.USERNAME);
        return String.join("/", directoryPathLocal, "/sftp", username);
    }

}
