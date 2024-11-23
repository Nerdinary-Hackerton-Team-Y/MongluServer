DROP DATABASE IF EXISTS mongle;
CREATE DATABASE IF NOT EXISTS mongle;
USE mongle;

CREATE TABLE `user` (
  `id` int AUTO_INCREMENT,
  `name` varchar(100),
  `username` varchar(100),
  `nickname` varchar(100),
  `password` varchar(100),
  `profile_picture_url` varchar(20),
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  `joined_at` datetime,
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`)
);

CREATE TABLE `quest` (
  `id` int AUTO_INCREMENT,
  `post_id` int,
  `title` varchar(100),
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`)
);

CREATE TABLE `post` (
  `id` int AUTO_INCREMENT,
  `user_id` int,
  `quest_id` int,
  `image_url` varchar(100),
  `title` varchar(100),
  `content` varchar(100),
  `is_quest` tinyint(1),
  `rank` int,
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`quest_id`) REFERENCES `quest`(`id`)
);

CREATE TABLE `comment` (
  `id` int AUTO_INCREMENT,
  `user_id` int,
  `post_id` int,
  `content` varchar(100),
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`post_id`) REFERENCES `post`(`id`)
);

CREATE TABLE `like_history` (
  `id` int AUTO_INCREMENT,
  `post_id` int,
  `user_id` int,
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`post_id`) REFERENCES `post`(`id`)
);

CREATE TABLE `hashtag` (
  `id` int AUTO_INCREMENT,
  `tag` varchar(100),
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`)
);

CREATE TABLE `post_hashtag` (
  `id` int AUTO_INCREMENT,
  `post_id` int,
  `hashtag_id` int,
  `status` VARCHAR(15) DEFAULT 'ACTIVATED',
  PRIMARY KEY (`id`),
  FOREIGN KEY (`post_id`) REFERENCES `post`(`id`),
  FOREIGN KEY (`hashtag_id`) REFERENCES `hashtag`(`id`)
);

CREATE TABLE `win_history` (
  `id` int AUTO_INCREMENT,
  `post_id` int,
  `user_id` int,
  `quest_id` int,
  `rank` int,
  `created_at` datetime,
  `updated_at` datetime,
  `deleted_at` datetime,
  PRIMARY KEY (`id`)
);

