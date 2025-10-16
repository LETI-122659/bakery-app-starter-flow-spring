package com.example.QRcode;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface QRcodeRepository extends JpaRepository<QRcode, Long>, JpaSpecificationExecutor<QRcode> {

    Slice<QRcode> findAllBy(Pageable pageable);
}
