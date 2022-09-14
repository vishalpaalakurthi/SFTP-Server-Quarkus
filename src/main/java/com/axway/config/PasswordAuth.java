package com.axway.config;

import com.axway.model.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Slf4j
public class PasswordAuth implements PasswordAuthenticator {

    @Inject
    @ConfigProperty(name = "sftp_user")
    String userName;

    @Inject
    @ConfigProperty(name = "sftp_password")
    String password;


    @Override
    public boolean authenticate(String userName, String password, ServerSession serverSession) throws PasswordChangeRequiredException, AsyncAuthException {
        log.info("Basic authentication");
        log.info("username {}", userName);
        serverSession.setAttribute(Attributes.USERNAME, userName);
        return userName.equals(this.userName) && password.equals(this.password);
    }
}
