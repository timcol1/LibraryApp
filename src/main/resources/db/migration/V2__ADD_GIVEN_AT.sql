ALTER TABLE book ADD COLUMN given_at timestamp;

update book set given_at = '2023-10-16 13:30:00' where id_person is not null