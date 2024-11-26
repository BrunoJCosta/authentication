-- this cpf is a random, i get in forDev

-- insert into users.personal_data(pk, cpf, gender, name)
-- values (1,'34164480068', 'Masculine', 'Bruno Jereissati da Costa')
-- on conflict do nothing;
--
-- -- password: bruno@123
-- insert into users.users(pk, email, password, date_created, personal_data_id)
-- VALUES (1,'bruno@gmail.com',
--         '$2a$12$yKL2i.1u/IbkiDgDmDjt.u5oetaYyXtwtdoSxFVhp2E3nNk3iroq.',
--         '2024-09-28 21:52:00', 1)
-- on conflict do nothing;

select exists(select * from users.users)