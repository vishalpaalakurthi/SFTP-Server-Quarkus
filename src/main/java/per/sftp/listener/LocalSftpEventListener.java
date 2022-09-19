package per.sftp.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.sftp.server.FileHandle;
import org.apache.sshd.sftp.server.SftpEventListener;

import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Dependent
public class LocalSftpEventListener implements SftpEventListener {
    //	@Override
//	public void closing(ServerSession session, String remoteHandle, Handle localHandle) throws IOException {
//		// TODO Auto-generated method stub
//		SftpEventListener.super.closing(session, remoteHandle, localHandle);
//		log.info("Closing " + localHandle.getFile().toString());
//	}
//
//	@Override
//	public void closed(ServerSession session, String remoteHandle, Handle localHandle, Throwable thrown)
//			throws IOException {
//		// TODO Auto-generated method stub
//		SftpEventListener.super.closed(session, remoteHandle, localHandle, thrown);
//		log.info("Closed");
//		session.getAttribute(Attributes.TRACKING).add(new ServerAction(Operation.CLOSE, localHandle.getFile(), localHandle.getFileHandle(), new Date()));
//	}
//
    @Override
    public void creating(ServerSession session, Path path, Map<String, ?> attrs) throws IOException {
        //throw new UnsupportedOperationException("Folder creation is not allowed");
        log.info("in creating files");
//		if(!Files.exists(path) && Files.isDirectory(path)){
//			path = Files.createDirectory(path);
//			log.info("in created directory");
//		}
//		SftpEventListener.super.creating(session, path, attrs);
    }

    @Override
    public void created(ServerSession session, Path path, Map<String, ?> attrs, Throwable thrown) throws IOException {
        // TODO Auto-generated method stub
//		if(!Files.exists(path) && Files.isDirectory(path)){
//			path = Files.createDirectory(path);
//			log.info("in created directory");
//		}
        SftpEventListener.super.created(session, path, attrs, thrown);

        log.info("Created");
    }

//	@Override
//	public void opening(ServerSession session, String remoteHandle, Handle localHandle) throws IOException {
//		// TODO Auto-generated method stub
//		SftpEventListener.super.opening(session, remoteHandle, localHandle);
//		log.info("Opening");
//	}

    //	@Override
//	public void opening(ServerSession session, String remoteHandle, Handle localHandle) throws IOException {
//		// TODO Auto-generated method stub
//		SftpEventListener.super.opening(session, remoteHandle, localHandle);
//		log.info("Opening");
//	}
//
//	@Override
//	public void open(ServerSession session, String remoteHandle, Handle localHandle) throws IOException {
//		// TODO Auto-generated method stub
//		localHandle.getFile().toFile().mkdirs();
//		SftpEventListener.super.open(session, remoteHandle, localHandle);
//		log.info("Open");
//		//session.getAttribute(Attributes.TRACKING).add(new ServerAction(Operation.OPEN, localHandle.getFile(), localHandle.getFileHandle(), new Date()));
//
//	}
//
//	@Override
//	public void openFailed(ServerSession session, String remotePath, Path localPath, boolean isDirectory,
//			Throwable thrown) throws IOException {
//		// TODO Auto-generated method stub
//		SftpEventListener.super.openFailed(session, remotePath, localPath, isDirectory, thrown);
//		log.info("Open failed");
//	}
//
//	@Override
//	public void read(ServerSession session, String remoteHandle, DirectoryHandle localHandle, Map<String, Path> entries)
//			throws IOException {
//		// TODO Auto-generated method stub
//		SftpEventListener.super.read(session, remoteHandle, localHandle, entries);
//		log.info("Read");
//	}
//
    @Override
    public void reading(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, byte[] data,
                        int dataOffset, int dataLen) throws IOException {
        // TODO Auto-generated method stub
        SftpEventListener.super.reading(session, remoteHandle, localHandle, offset, data, dataOffset, dataLen);
        log.info("Reading");
    }

    @Override
    public void read(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, byte[] data,
                     int dataOffset, int dataLen, int readLen, Throwable thrown) throws IOException {
        // TODO Auto-generated method stub
        SftpEventListener.super.read(session, remoteHandle, localHandle, offset, data, dataOffset, dataLen, readLen, thrown);
        log.info("Read 2");
        //session.getAttribute(Attributes.TRACKING).add(new ServerAction(Operation.READ, localHandle.getFile(), localHandle.getFileHandle(), new Date()));

    }

    @Override
    public void writing(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, byte[] data,
                        int dataOffset, int dataLen) throws IOException {
        // TODO Auto-generated method stub
        //SftpEventListener.super.writing(session, remoteHandle, localHandle, offset, data, dataOffset, dataLen);
        log.info("Writing");
    }

    @Override
    public void written(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, byte[] data,
                        int dataOffset, int dataLen, Throwable thrown) throws IOException {
        log.info("Written done");
        //execute(null, data);
        //log.info("Message executed");
    }

    @Override
    public void moving(ServerSession session, Path srcPath, Path dstPath, Collection<CopyOption> opts)
            throws IOException {
        log.info("moving started from src ->> {} to dest ->> {}", srcPath, dstPath);
        //throw new UnsupportedOperationException("Folder/File renaming is not allowed");
    }

    @Override
    public void moved(ServerSession session, Path srcPath, Path dstPath, Collection<CopyOption> opts, Throwable thrown)
            throws IOException {
        // TODO Auto-generated method stub
        SftpEventListener.super.moved(session, srcPath, dstPath, opts, thrown);
        log.info("file moved from src ->> {} to dest ->> {}", srcPath, dstPath);
        log.info("Moved");
    }

    @Override
    public void removing(ServerSession session, Path path, boolean isDirectory) throws IOException {
        // TODO Auto-generated method stub
        SftpEventListener.super.removing(session, path, isDirectory);
        log.info("Removing");
    }

    @Override
    public void removed(ServerSession session, Path path, boolean isDirectory, Throwable thrown) throws IOException {
        // TODO Auto-generated method stub
        SftpEventListener.super.removed(session, path, isDirectory, thrown);
        log.info("Removed");
        //session.getAttribute(Attributes.TRACKING).add(new ServerAction(Operation.DELETE, path, "", new Date()));

    }
}
