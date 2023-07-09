BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
	role varchar(20),
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);

CREATE TABLE account
(
    account_id serial NOT NULL,
    user_id integer,
    balance integer,
    CONSTRAINT accounts_pkey PRIMARY KEY (account_id),
    CONSTRAINT accounts_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (user_id)
);


CREATE TABLE transfer
(
    transfer_id serial NOT NULL,
    user_from_id integer,
    user_to_id integer,
    amount integer NOT NULL,
    transfer_type character varying(50) NOT NULL,
    transfer_status character varying(50) NOT NULL,
    CONSTRAINT transfer_pkey PRIMARY KEY (transfer_id),
    CONSTRAINT transfer_user_from_fkey FOREIGN KEY (user_from_id) REFERENCES users (user_id),
    CONSTRAINT transfer_user_to_fkey FOREIGN KEY (user_to_id) REFERENCES users (user_id),
    CONSTRAINT transfer_transfer_type_check CHECK (transfer_type::text = 'Send'::text OR transfer_type::text = 'Request'::text),
    CONSTRAINT transfer_transfer_status_check CHECK (transfer_status::text = 'Pending'::text OR transfer_status::text = 'Approved'::text OR transfer_status::text = 'Rejected'::text)
);

CREATE TABLE account_transfer
(
    account_id integer NOT NULL,
    transfer_id integer NOT NULL,
    CONSTRAINT pk_account_transfer PRIMARY KEY (account_id, transfer_id),
    CONSTRAINT account_transfer_account_id_fkey FOREIGN KEY (account_id) REFERENCES account (account_id),
    CONSTRAINT account_transfer_transfer_id_fkey FOREIGN KEY (transfer_id) REFERENCES transfer (transfer_id)
);


INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER'); -- 1
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER'); -- 2
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER'); -- 3

INSERT INTO transfer(amount, transfer_type, transfer_status) VALUES ('10', 'Send', 'Approved');
INSERT INTO transfer(amount, transfer_type, transfer_status) VALUES ('10', 'Request', 'Pending');

INSERT INTO account(user_id, balance) VALUES ('1','1000');
INSERT INTO account(user_id, balance) VALUES ('2','-1000');

COMMIT TRANSACTION;
