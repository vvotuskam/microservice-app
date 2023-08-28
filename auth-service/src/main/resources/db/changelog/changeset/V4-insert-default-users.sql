INSERT INTO USERS(id, email, role)
VALUES (
           'ccde2c48-2b24-4a1b-aa23-9db60f489792',
           'user@user.com',
           'ROLE_USER'
       );

INSERT INTO USERS(id, email, role)
VALUES (
           'd96288d9-3eba-43e9-92c4-0ea7fb633730',
           'user@admin.com',
           'ROLE_ADMIN'
       );

INSERT INTO PASSWORDS(user_id, password)
VALUES ('ccde2c48-2b24-4a1b-aa23-9db60f489792',
        '$2a$12$uXv.9/POxfia3qZxT8KBsuOhuIfB.CCuvW.Le3ziIehk1unte4mqq'); -- password

INSERT INTO PASSWORDS(user_id, password)
VALUES ('d96288d9-3eba-43e9-92c4-0ea7fb633730',
        '$2a$12$8yOD6x0n3Zzz4PzzeaS7iumOWLnCKQGY8/RG9ZEXFyovrUepD.Yiu'); -- password