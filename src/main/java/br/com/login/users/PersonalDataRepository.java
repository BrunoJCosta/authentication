package br.com.login.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {

    Optional<PersonalData> findByCpfAndActiveTrue(String cpf);

}