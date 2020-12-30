package com.AccountsService.Server;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>{
	
	public Optional<Transfer> findById(int id);
	public Optional<List<Transfer>> findBySendingAccountid(int sendingAccountid);
	public Optional<List<Transfer>> findByReceivingAccountid(int sendingAccountid);
	
	

}
