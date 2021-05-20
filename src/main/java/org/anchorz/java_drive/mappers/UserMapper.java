package org.anchorz.java_drive.mappers;
import org.anchorz.java_drive.models.Note;
import org.anchorz.java_drive.models.UserProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE email = #{email}")
    UserProfile getUser(String email);

    @Insert("INSERT INTO USERS (email, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(UserProfile user);

}
