package org.rbat.players.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.rbat.players.entity.PlayerEntity;

@Repository
public interface PlayerRepository
        extends JpaRepository<PlayerEntity, Long> {
}
