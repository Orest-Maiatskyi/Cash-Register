
# MEASUREMENT TABLE

CREATE TABLE `measurements` (
  `id` int NOT NULL AUTO_INCREMENT,
  `measurement` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

# GOODS TABLE

CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(8) NOT NULL,
  `measurement_id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `price` decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `measurement_id` (`measurement_id`),
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`measurement_id`) REFERENCES `measurements` (`id`)
);

# STORAGES TABLE

CREATE TABLE `storages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


# INCOME TABLE

CREATE TABLE `income` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storage_id` int NOT NULL,
  `good_code` varchar(255) NOT NULL,
  `quantity` float DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `storage_id` (`storage_id`),
  KEY `good_code` (`good_code`),
  CONSTRAINT `income_ibfk_1` FOREIGN KEY (`storage_id`) REFERENCES `storages` (`id`),
  CONSTRAINT `income_ibfk_2` FOREIGN KEY (`good_code`) REFERENCES `goods` (`code`)
);


# OUTCOME TABLE

CREATE TABLE `outcome` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storage_id` int NOT NULL,
  `good_code` varchar(255) NOT NULL,
  `quantity` float DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `storage_id` (`storage_id`),
  KEY `good_code` (`good_code`),
  CONSTRAINT `outcome_ibfk_1` FOREIGN KEY (`storage_id`) REFERENCES `storages` (`id`),
  CONSTRAINT `outcome_ibfk_2` FOREIGN KEY (`good_code`) REFERENCES `goods` (`code`)
);

# SCRAPPED TABLE

CREATE TABLE `scrapped` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storage_id` int NOT NULL,
  `good_code` varchar(255) NOT NULL,
  `quantity` float DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `storage_id` (`storage_id`),
  KEY `good_code` (`good_code`),
  CONSTRAINT `scrapped_ibfk_1` FOREIGN KEY (`storage_id`) REFERENCES `storages` (`id`),
  CONSTRAINT `scrapped_ibfk_2` FOREIGN KEY (`good_code`) REFERENCES `goods` (`code`)
);

# WAREHOUSE TABLE

CREATE TABLE `warehouse` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storage_id` int NOT NULL,
  `good_code` varchar(255) NOT NULL,
  `quantity` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index` (`storage_id`,`good_code`),
  KEY `good_code` (`good_code`),
  CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`storage_id`) REFERENCES `storages` (`id`),
  CONSTRAINT `warehouse_ibfk_2` FOREIGN KEY (`good_code`) REFERENCES `goods` (`code`)
);

# STATUSES TABLE

CREATE TABLE `statuses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
);

# ORDERS TABLE

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `orders_ibfk_1` (`status_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`)
);

# ORDERED TABLE

CREATE TABLE `ordered` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `good_code` varchar(8) NOT NULL,
  `quantity` float NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `good_code` (`good_code`),
  CONSTRAINT `ordered_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `ordered_ibfk_2` FOREIGN KEY (`good_code`) REFERENCES `goods` (`code`)
);

# ROLES TABLE

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT NULL,
  `actions` json DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `roles` (`role`, `actions`)
VALUES
    ("cashier", '{"MAIN": ["Order List"], "ORDERS": ["Create Order", "Add Good To Order", "Update Good Quantity", "Close Order"]}'),
    ("senior cashier", '{"ORDERS": ["Cancel Order", "Cancel Ordered Good"], "REPORTS": ["Create X Report", "Create Z Report"]}'),
    ("commodity expert", '{"MAIN": ["Good List", "Warehouse Goods List"], "GOODS": ["Create Good", "Update Good", "Add Good", "Write Off The Goods"]}'),
    ("admin", '{"USERS": ["Add User", "Delete User"]}');


# USERS TABLE

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `avatar` text,
  `password_hash` varchar(44) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);

INSERT INTO `users` (`role_id`, `first_name`, `last_name`, `email`, `avatar`, `password_hash`)
VALUES (4, "Admin", "Default", "admin@gmail.com", "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAACXBIWXMAAAsTAAALEwEAmpwYAAAPQ0lEQVR4nO1b6VMbW3bn00w+JPkTMtmm5q+YyuQ/SFL5nKm8fBp3C2z2XWCMDajFYsASSOqrDbRLIAFiB7Nji90Gr7Lfm3r2m+d53p6xx6n4ndS5t1uopRaSMHiclG/VqULSvbfPOfec31n6UlDwZXwZX8aXcUZD+1vyFzqO/EbgSaXAE1HgxRU9J8b1vPhc4Ml/I+Hf+B3+xuaQSr3G8k9dmq6fF/xfHF0ax18LGvErgSNTAie+1fMETkLS2kk9Z/lP3LPgcx8dReZf0RNMErpdQ8B20Q5TZAA2Im6IL3vh2Z4fXt0PwNuvg5Tw7+/3/PQ3nINzcY1ek6IMjljwGQWf22j/neXvBV706DnyQRba3WaH7agbXt0LwPvfB09EL+/6YXvMDe42B92TKoMjHwRedOl4+9/9ueUuQB/V82KtnhffIXOdRVaYsAzQEz6p0JkILWTCPECfwaxCfCdwpObPhhOtheIv9Zy4SZnREBg1OuH5QX6C/+lrRvmseb4fgBHDQMI9BJ5s6M+Z/vGTCq/TWP5dz5MfkQFLvQ3iK76spnww64EZ2yC4WhxgqbdDb5l8kgTaCwn0VVvBo3PAk63j95IJ8cJcZ5f3eK3jxX/7JMILHOFkXx/qdsLrB+o+/vZxAHYnPDB4xaEAs2zk9rnhdnwIXjwMUoXtTXgoUKo948eHAYj0DkiWIP4kcGLxmQqv50i9bPLrQbcqU4fxACy6XNBbfnTC+kICVxod0HBtEKoDXqiY8EPpfABKVkNQvBqif1eM+6Ey4oOq7RGouzMJV6bCifWGCisFw0zWsOp3JSlZrD0T4QWOaPABHRor7EQ9qozcnfOAqcaWYLytygZa0QVlC0EovhHKSpU7THhKB5PQNDQErVoH3atfaz/WJXbG3JQ3FjIJd/o+z5EPqGU14d/EAzDc7VQIjiddsp5daFXhJarYGaW/VY34oWwmAGQ/DN/GQ3BnzguHj9SVgDwKnPiTnrP+6+mhPU9eo2BqZv/Dvh/sTezUhSIr1ItuKFnN7cRzET51Tos3SJ/lbLbDy7sBdXegbmp93aax/sPHx3mOhToEm9SHPdn0gbGSCa8rt0HlmD8vwfMVHkl7Mwq6GuYWljobPLuVHnqHe5k1ChpxS6v1/uzECtDTJIeFOkTc1HjcV8WEv6J1UCBLZrpsMQily8GPFr4qFgHt9rhiTv3mOLQ2MiWYa200zKZGBzlECrxYfSLhdefMv9Dz5BB9Kr6sjM1vHgYSZt9Sa4fSpSNBS1eC0NzqZKissVLkL17PX3jEj0t96NNWuldLuwvqb00k5qFyWyQlOJrsaZiAuQnjQXx/orRZwNyeZxlemon1MBNrq7Slnby2j/lgx3kbY54ncPGaK++Tv9TvSYTQjvPsNJttfro2oeyFAOiq2W+YgqfyOXJNcgWeDOQlfEeR+VeI+l3nSVp6e3feK4MMVEZ9aSfbKpne1PhtmJo+AL2Uu9cTd87CNw76pAzRCtNTd2Bm6i79jCExVZENi6OgP8+egWE4FaCxdhB48X8QzPM4fSKqaRWTHDnON/QpT5WeyHIQBMn0b+x8B5t3foBoeIeFJg2BBn8gq/Da4aBkugTGhrfpHrFbz6Cj0Er3kHElWZHNbhYZTLW2NFcYN0mZIieac29m8OQQy8/Uqm7BNchOosamGuqqhtjJXdP6KOMyhVxrjIlCK1wcD2cUvm4yBIJ0mqGBNcUepktD9Ht8RpoVHUyArp6ZO4bBZJ6/32VYIHDiYWuZ+a+yKkDPk//CjbAwSUXWnhLGXK3Hq4rqDSbm/y7jnIJ5JI/pOlPCeSs0zo+kCV+zOAxCObMul2E2bb3XssDwhHjSrAgJFUuVX55uBe5W5pbtnPjb7ObPkSmcjM2M5E1iw64E8BWvq4e35maGymNDO2kCXF+MQ3f5YALYhGJm0kIRAV2FHYQSJjzOWVx5nLZ+fHSPWV+bS1UB9QeToGtgVoAZYTLvW6NuOSRGs5h/189p60lD0mIrZl64SZ2dgVkarYeoUDhnef0byvT6zncw7L4B12ok4MyDDLU+CDpWE1iyuvEtE6LERusENSVc8oboHK+gtN6XdwLMDXhyeGwDRa+x/jNuYGuyq2+AIJQS9mTCCg/XXi0bgM0Ddmp9DQyckLrKBsHcPg4uxxoEwrsQnrkP4dkHEJ69D0PjBxAY3oMB2wpYOiegq3TgaF2JE0aDW3S/3ioWGrUro6oKwIQJARj7C6kHaG2U3IAXf53Z/HlSSUMYUaL/wQw7wZY6e8asrs7JzMzWHqXMruz+ARz982BqHQWPdwNGFx/B6NLj3GjxEfhC22BsHqZ79l0M0T2dV6fo56bAkKoCkFovMeUhz8kyTIrM/QSelGe2AE4kOGlzROlD2JjA7xsNgxkV0NTOHjw0uA4Lm09yFzYLhcb2ITx9D+Zj30LYu8FS7x51IKQh0cosEXlOlmEj7M4eDgV8acETiK8otYdtLPy+JgP60wRIysjCI7dOTfhUGpk4YIVXrSOjApoizGp87UoceLiUwKHFzBbAk8c46Y+3lf4jFxZYl6sJX7oQTGRuI9fjZ6eAxTi0X7BTLNJuKQskmWpmmQKQ52QZMKehCuDEeGYL4MgPOAlfVCQvluN/WQYApNkbTYACZya8TAYpIboYPUqokhOr0uuMF+RZAeR3JQXw5NlxGPAeJyU3Id99HUi8lEiu+pLz8WbCNhe7p3IWxO2JQWfpACWPO5bzOuu1OVYYWf2qKTX2GZk1Enj/jbJJK7XL/pRdAY8D8O6+Hd7e7ofDXcORAlJ6e4kuTSsDSdfAes6CYFhMDpG5rkNl0YgkuNTL6BXJHTUE3u4aqAxUlkf+7AoQJBd4sW2Bt3uGBPWUiswF5o5cIDkflzs0wUjuANglZ4WYO5S7co8K0X2WkUpAmJpSly2wk+6+QBQyPN+05OACPAPBP6waFYvFWqYAbF2rlbS6MpbGhsYPcj9JKaTRzM23mfO64el77CTL7WnCU96i7KRN1aJChu+WjTmAIM/C4IPJPsVi92WmvVqXR7Web5NCYDCylxegyQrIZ01oVLKAavWkrNrHskVvi1khw73xvhzCIMcSoZjXpFg8azbRxdiiUkPey5dZEeIky8cyb9ZHwdAYoqconyTS8PR9+tlwMQgmIXrsHphK45rLVxzqFamRFW1TfUoF3HT3Z0+E9JxYgZMmjUoFHIww7bU2Dagib72dZVndNV4YWcic8hqbWAjDFpfc5kLqLHZAxwX2GRWUUQGLj6Cnnpl4nUO9KMM3UPj73rDSiicMZvl5ZRkVoOPIb3CStUEJgi9iRhYJMAGJRVWRF9viNBR2ZQ6FkZn70NcSSbzG6rsSYZ+l1234OTxzL+N60jvLMsFyG22+pgqPb6AwSUJen99U4hipE7MXQ120HLa8Q2ae3zhajOS5wnDgkiug2rdvnIrQOp8CkG4MIvMPMiti7gEl+TNWhcmfUwmzS0vHRKKXUBn2qRdkkiUir8m8/3DDmFs5jIPeycGCyN+v2OTWMPMhbHomv/JKBsWLU5FES6u72kMrunwATo0CQ7vQU8tabboLVqgeVhceeWqR+pUbXiXv+FmKAGMF2YagEb/Cye7LShB5s2sAYwUzoxqXRzUc1sei0HrlqJZHwpLWG9jKsxx+DP7QNnWJ5L2w4sz02q3Gx4qda2UivN5Wmv/gJTkHsP5Hrk3Rt+hHT5eVG604TYmXn1VbEWVPbiwMwoWjN8M91V7FvQBMdhAfBp1rEBy5RZF/ZCFOCf0+OHobBgfWgVydhqsV0vs9BMwiG9tL+txaY4fyGWVNUrJ2dPqLdiWAP11i5q/nxcOecz1/mVUByW3xaK/SCn7cMYBYx7TZbPcrS1BJWEvrKIxP3YOFjacwMX0PrJ2T0HGBIXM+1F3hApdpAWaWvoaF2FOIDO9Bbw2L8W2lNiibPVKC1swU1lcpwustJXaN9Zrza4snXozw4ofOIgLP1pRWcH+inwlbSKBxZoS2p2S/91iXKbMofDLN3/gWwsO74DTMgelymArXVYI3R6y0399Z4oTeWi8QIQpucQmi43dU95lb/QZMlyMJLCpew8zPlwDf1ND3/aoROulv4oe87xHpOeLGTSNdSiugWu2WtFpph9bLLKd39s6mMXwWhEroqWaW0GgchLYKZvoj3el8DndKsZ8TnQX5js5C29/IFx4xjUzeGEHG1pAoLuiJzt988kkUgDQSuaVwF4zxrzaVwmM6L4W+tx1F5G8LTjIEjtTgA8w1IrzcULoCuoapikUFD1n6ZMJTij2Fa1JoRL//fk0p/MsNAy2GpNOvKjjp6MLECO/hYb7eoUwukLBqXHf1Q3zHrTyh0dvg6l84HauIPQEPWWa4kPT9zoIH5kUTPFlSHgzSULtknZwY+6gLEjgQPOQrMssOZYhJpj/u22B56wH1UXw3gPPHp+5+tAKm5h6wMFo2QPde2XpAn5WJjyW7lPTw5NVHX5GRB15C1PPiT+hTWykZYjId3u6HEGEdmf5LQwpB3GQJ/APrWQUOuG+Cz7mm+A73wj0DYgDe3M78fMxepXD8QeDJvxSc+gVJHq/JHa+EObMJuosJLFyfTwgwfT3OwLLcpRCs7+IQ9DcpFXVVapVNLzxKfDc/N0f3RJM/TnjkjUanc5bfFZzF0Et3hlDLx7mDnIq+2idwsB+FoHuFVYrCmEJYGcWTvxPbRllzxb0CB7dG4cU+a21hb/JYs5eF50jNmQgvDz0vnqPugEzqzWnRQY0W7SweL4ZcsLs3BevbMVjcvJ9QwMrWXbi5cxN29ybhup9ZwNIxCj5Ce+NRrEezP6uTTx14CVHPi2/kEInZ4XGMYgodn+5jXdrkIqVZpIWKYv6uAR7N9MGbneOFx9wkEep48urUfT7bQITFe3iyS0Q6zWnx+CwI09ukU6eh7tTQPt+BMRbv4QkaQt8nYN6NxUdqFXkahFUd7s1ye5bhYZLz0XH+NAbeKdQVEregYdiAFuFqtsCGr592Y04qNK7FZgat549ug3/A3B5T9YLPbbQWir/UXbA6hUL8VziJYQ0BUi/CpNEMMU8/9V08zRcxhg1IL24a6Xf4G87BBibt4Sn/z+AQS9pP/t8hJxl4G0sosn2Fd3IE/uT/NodNDGxjYScn52bG5za0Wu/PsBOLNzPwBAVOXBJ48pC+hsN3kZz4Hv/G7/ClBZ3Dk3Jc81n495dR8P9//C9gxyL8Yks65AAAAABJRU5ErkJggg==", "FX2VxVpXCCBTps/FPTYSplrMeKZbNL4G/atanC4PmD0=");



