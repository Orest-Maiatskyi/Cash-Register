SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

INSERT INTO `measurements` (`measurement`)
VALUES
    ("PC"),
    ("KG"),
    ("L");

INSERT INTO `goods` (`code`, `measurement_id`, `title`, `description`, `price`)
VALUES
    ("10000000", 1, "special", "special", 99.99),
    ("00000001", 1, "TITLE-1", "DESCRIPTION-1", 99.99),
    ("00000002", 2, "TITLE-2", "DESCRIPTION-2", 999.99),
    ("00000003", 3, "TITLE-3", "DESCRIPTION-3", 9999.99);

INSERT INTO `storages` (`address`)
VALUES
    ("вулиця Авторемонтна, 85, Київ, 03124"),
    ("вулиця Якутська, 10, Київ, 02000"),
    ("вулиця Пшенична, 13, Київ, 02450");

INSERT INTO `income` (`storage_id`, `good_code`, `quantity`)
VALUES
    (1, "00000001", 30.0),
    (2, "00000002", 30.0),
    (3, "00000003", 30.0);

INSERT INTO `outcome` (`storage_id`, `good_code`, `quantity`)
VALUES
    (1, "00000001", 10.0),
    (2, "00000002", 10.0),
    (3, "00000003", 10.0);

INSERT INTO `scrapped` (`storage_id`, `good_code`, `quantity`)
VALUES
    (1, "00000001", 10.0),
    (2, "00000002", 10.0),
    (3, "00000003", 10.0);

INSERT INTO `warehouse` (`storage_id`, `good_code`, `quantity`)
VALUES
    (1, "00000001", 10.0),
    (2, "00000002", 10.0),
    (3, "00000003", 10.0);

INSERT INTO `statuses` (`status`)
VALUES
    ("pending"),
    ("closed"),
    ("canceled");

INSERT INTO `orders` ()
VALUES
    (),
    (),
    ();

INSERT INTO `ordered` (`order_id`, `good_code`, `quantity`)
VALUES
    (1, "00000001", 5.0),
    (2, "00000002", 5.0),
    (3, "00000003", 5.0);

INSERT INTO `users` (`role_id`, `first_name`, `last_name`, `email`, `avatar`, `password_hash`)
VALUES
    (1, "Steve", "Madden", "cashier@gmail.com", "base64encodedImageHere", "FX2VxVpXCCBTps/FPTYSplrMeKZbNL4G/atanC4PmD0="),
    (2, "Guy", "Ritchie", "senior_cashier@gmail.com", "base64encodedImageHere", "FX2VxVpXCCBTps/FPTYSplrMeKZbNL4G/atanC4PmD0="),
    (3, "Vasya", "Pupok", "commodity_expert@gmail.com", "base64encodedImageHere", "FX2VxVpXCCBTps/FPTYSplrMeKZbNL4G/atanC4PmD0=");
