package com.rbac.springsecurity.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class ObjectSerializer {

    private ObjectSerializer(){};
    private final static Logger LOG = LoggerFactory.getLogger(ObjectSerializer.class);

    /**
     * 序列化
     */
    public static String serialize(Object target) {
        if (target == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        String base64Encode = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(target);
            base64Encode = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            LOG.error("serialize object io exception", e);
        } finally {
            try {
                byteArrayOutputStream.close();
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return base64Encode;
    }

    /**
     * 反序列化
     */
    public static Object deserialize(String base64Encode) {
        if (base64Encode == null) {
            return null;
        }
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Encode));
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayOutputStream);
            object = objectInputStream.readObject();
        } catch (IOException e) {
            LOG.error("deserialize object io exception", e);
        } catch (ClassNotFoundException e) {
            LOG.error("deserialize class not found", e);
        } finally {
            try {
                byteArrayOutputStream.close();
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }

}
