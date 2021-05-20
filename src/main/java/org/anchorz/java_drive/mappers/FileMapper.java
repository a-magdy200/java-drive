package org.anchorz.java_drive.mappers;
import org.anchorz.java_drive.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files (name, type, size, url, userId) VALUES (#{name}, #{type}, #{size}, #{url}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(File file);

    @Select("SELECT * FROM files WHERE userId=#{userId}")
    List<File> getAllFiles(int userId);

    @Select("SELECT * FROM files WHERE fileId=#{fileId}")
    File getFile(int fileId);

    @Select("Select * FROM files WHERE name=#{name} AND userId=#{userId}")
    File checkFile(String name, int userId);

    @Delete("DELETE FROM files WHERE fileId=#{fileId}")
    void deleteFile(int fileId);
}
