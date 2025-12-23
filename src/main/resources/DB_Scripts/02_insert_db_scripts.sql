INSERT INTO app_role (role_id, role_code, role_name, description, status_code, created_by, updated_by, updated_date_time
) VALUES
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR', 'Administrator', 'Full system access', 'Y', 'SYSTEM','SYSTEM', current_timestamp),
('1566ee33-b1e6-4403-ad0f-9514535fc52f','STANDARD_USER', 'Standard User', 'Basic access to application features, mostly read-only', 'Y', 'SYSTEM','SYSTEM', current_timestamp);

INSERT INTO app_permission (permission_id, permission_code, permission_name, description, created_by, updated_by, updated_date_time
) VALUES
('2bf327b2-dd1c-46b1-82e3-f6fb86382f4f','VIEW_DASHBOARD', 'View Dashboard', 'Allows viewing of dashboard data', 'SYSTEM','SYSTEM', current_timestamp),
('8b04aade-39a0-45f4-a117-f451a21c4145','ADD_USER', 'Adding a User', 'Allows add user', 'SYSTEM','SYSTEM', current_timestamp),
('3b7b01e3-9d62-4df9-b25f-636dfde332b4','EDIT_USER', 'Edit User', 'Allows editing user details', 'SYSTEM','SYSTEM', current_timestamp),
('aaeb207a-dacf-4858-a459-64f1b6e3c8a3','DELETE_USER', 'Delete User', 'Allows deleting user accounts', 'SYSTEM','SYSTEM', current_timestamp),
('0c7a8d5e-ec95-46b6-93e2-23cf8bcf83e7','MANAGE_ROLES', 'Manage Roles', 'Allows creation and modification of roles', 'SYSTEM','SYSTEM', current_timestamp),
('fb518a3f-9953-433f-9a9b-0d4dec65fabc','MANAGE_PERMISSIONS', 'Manage Permissions', 'Allows assigning and revoking permissions', 'SYSTEM','SYSTEM', current_timestamp);

--ADMINISTRATOR
INSERT INTO app_role_permission
(role_id, role_code, permission_id, permission_code, created_by, updated_by, updated_date_time)
VALUES
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR','2bf327b2-dd1c-46b1-82e3-f6fb86382f4f','VIEW_DASHBOARD','SYSTEM','SYSTEM',CURRENT_TIMESTAMP),
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR','8b04aade-39a0-45f4-a117-f451a21c4145','ADD_USER','SYSTEM','SYSTEM',CURRENT_TIMESTAMP),
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR','3b7b01e3-9d62-4df9-b25f-636dfde332b4','EDIT_USER','SYSTEM','SYSTEM',CURRENT_TIMESTAMP),
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR','aaeb207a-dacf-4858-a459-64f1b6e3c8a3','DELETE_USER','SYSTEM','SYSTEM',CURRENT_TIMESTAMP),
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR','0c7a8d5e-ec95-46b6-93e2-23cf8bcf83e7','MANAGE_ROLES','SYSTEM','SYSTEM',CURRENT_TIMESTAMP),
('98011b9c-fd3d-4986-8792-89e4a897382c','ADMINISTRATOR','fb518a3f-9953-433f-9a9b-0d4dec65fabc','MANAGE_PERMISSIONS','SYSTEM','SYSTEM',CURRENT_TIMESTAMP);

--STANDARD_USER
INSERT INTO app_role_permission
(role_id, role_code, permission_id, permission_code, created_by, updated_by, updated_date_time)
VALUES
('1566ee33-b1e6-4403-ad0f-9514535fc52f','STANDARD_USER','2bf327b2-dd1c-46b1-82e3-f6fb86382f4f','VIEW_DASHBOARD','SYSTEM','SYSTEM',CURRENT_TIMESTAMP);
