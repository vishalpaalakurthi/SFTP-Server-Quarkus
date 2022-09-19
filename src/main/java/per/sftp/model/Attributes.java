package per.sftp.model;

import org.apache.sshd.common.AttributeRepository;

public final class Attributes {
    public static final AttributeRepository.AttributeKey<String> USERNAME = new AttributeRepository.AttributeKey<>();
    private Attributes() {

    }
}
