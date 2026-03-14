package com.rent.mancer.repository;


import com.rent.mancer.models.IdentityFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityFileRepository extends JpaRepository<IdentityFile,Long> {
}
