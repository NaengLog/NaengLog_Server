package com.homework.naenglog.domain.upload.repository;

import com.homework.naenglog.domain.upload.entity.Upload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends CrudRepository<Upload, Long> {
}
