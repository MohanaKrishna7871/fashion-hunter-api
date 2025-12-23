--Password for this hash - "Admin@123"
INSERT INTO app_user_login (user_id, user_name, password_hash, email, status_code, is_locked, created_by, created_date_time, updated_by, updated_date_time) 
VALUES ('1695d43f-9edf-47dd-a9da-4d9ee3e0e08d', 'ADMIN', '$2a$12$BXINU1LFOZuvwlE60nlwYe3zJHN5yQgIm8nPOjjz1V8fO55VGn/Ja', 'ADMIN@COMPANY.COM', 'Y', 'N', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO app_user_details (user_details_id, user_id, first_name, last_name, phone_number, created_by, created_date_time, updated_by, updated_date_time) 
VALUES (uuid_generate_v4(), '1695d43f-9edf-47dd-a9da-4d9ee3e0e08d', 'System', 'Administrator', '0000000000', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO app_user_role (user_id, user_name, role_id, role_code, created_by, created_date_time, updated_by, updated_date_time) 
VALUES ('1695d43f-9edf-47dd-a9da-4d9ee3e0e08d', 'ADMIN', '98011b9c-fd3d-4986-8792-89e4a897382c', 'ADMINISTRATOR', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);


-- Static - 'Address', 'PhoneNumber', 'Email'
INSERT INTO app_site_config(shop_code, config_key, config_value, status_code, created_by, created_date_time, updated_by, updated_date_time)
	VALUES ('REGISTERED_OFFICE', 'SITE_ADDRESS', 'No.22 Convent Garden, Behind St. Joseph College, Kangayam Road, Tirupur – 641604, TamilNadu, India.', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP),
	--('REGISTERED_OFFICE', 'SITE_PHONE', '+91 421 4250014', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP),
	('REGISTERED_OFFICE', 'SITE_EMAIL', 'prakash@fashionhunter.in', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP),
    ('REGISTERED_OFFICE', 'SITE_WEB', 'www.fashionhunter.in', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO app_site_config(shop_code, config_key, config_value, status_code, created_by, created_date_time, updated_by, updated_date_time)
	VALUES ('FACTORY', 'SITE_ADDRESS', 'SF No.216, Kasthuri Bai Nagar, Kanakampalayam, Tirupur – 641666, TamilNadu, India.', 'Y', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);
    