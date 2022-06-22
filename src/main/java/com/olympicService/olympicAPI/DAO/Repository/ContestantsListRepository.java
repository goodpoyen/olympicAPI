package com.olympicService.olympicAPI.DAO.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olympicService.olympicAPI.DAO.Entity.ContestantsList;

public interface ContestantsListRepository extends JpaRepository<ContestantsList, Long> {

}
