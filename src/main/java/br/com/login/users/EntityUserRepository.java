package br.com.login.users;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface EntityUserRepository extends JpaRepository<EntityUser, Long> {

    Optional<EntityUser> findByEmailAndActiveTrue(String email);

    @Query("select u from EntityUser u join u.permission p where u.active" +
            " and u.personalData.name ilike ?1 " +
            " and u.personalData.cpf ilike ?2 " +
            " and u.personalData.gender ilike ?3 " +
            " and (?4 = '' or u.email ilike ?4) " +
            " and (?5 is null or p = ?5)"
    )
    List<EntityUser> listAll(String name, String cpf, String gender, String email,
                             Integer permission, Pageable pageable);
}
