DELETE FROM PUBLIC.T_USER;

DELETE FROM PUBLIC.T_PROFILE;

INSERT INTO PUBLIC.T_PROFILE(OID, VERSION, CREATE_ACCOUNT, CREATE_DATE, UPDATE_ACCOUNT, UPDATE_DATE, ADDRESS, ADMIN) VALUES
('profile1', 0, NULL, TIMESTAMP '2010-12-30 10:47:58.437', NULL, NULL, 'Taipei', 'Y');
INSERT INTO PUBLIC.T_USER(OID, VERSION, CREATE_ACCOUNT, CREATE_DATE, UPDATE_ACCOUNT, UPDATE_DATE, NAME, GENDER, PROFILE_ID) VALUES
('user1', 0, NULL, TIMESTAMP '2010-12-30 10:47:58.431', NULL, NULL, 'Joe', 'Male', 'profile1');

INSERT INTO PUBLIC.T_PROFILE(OID, VERSION, CREATE_ACCOUNT, CREATE_DATE, UPDATE_ACCOUNT, UPDATE_DATE, ADDRESS, ADMIN) VALUES
('profile2', 0, NULL, TIMESTAMP '2010-12-30 10:47:58.437', NULL, NULL, 'Taipei', 'N');
INSERT INTO PUBLIC.T_USER(OID, VERSION, CREATE_ACCOUNT, CREATE_DATE, UPDATE_ACCOUNT, UPDATE_DATE, NAME, GENDER, PROFILE_ID) VALUES
('user2', 0, NULL, TIMESTAMP '2010-12-30 10:47:58.431', NULL, NULL, 'Lily', 'Female', 'profile2');
