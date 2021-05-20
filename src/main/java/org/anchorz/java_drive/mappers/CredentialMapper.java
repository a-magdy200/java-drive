package org.anchorz.java_drive.mappers;
import org.anchorz.java_drive.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO credentials (url, key, username, password, userId) VALUES (#{url}, #{key}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);

    @Select("SELECT * FROM credentials WHERE userId=#{userId}")
    List<Credential> getAllCredentials(int userId);

    @Select("SELECT * FROM credentials WHERE credentialId=#{credentialId}")
    Credential getCredentials(int credentialId);

    @Update("UPDATE credentials SET url=#{url}, key=#{key}, username=#{username}, password=#{password} WHERE credentialId=#{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialId=#{credentialId}")
    void deleteCredential(int credentialId);
}
