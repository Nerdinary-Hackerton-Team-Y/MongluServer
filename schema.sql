DROP DATABASE IF EXISTS mongle;
CREATE DATABASE IF NOT EXISTS mongle;
USE mongle;

<<<<<<< HEAD
CREATE TABLE `User` (
  `id` int,
=======
CREATE TABLE `user` (
  `id` int AUTO_INCREMENT,
>>>>>>> main
  `name` varchar(100),
  `username` varchar(100),
  `nickname` varchar(100),
  `password` varchar(100),
<<<<<<< HEAD
  `profilePictureUrl` varchar(20),
  `status` enum('activated', 'deactivated'),
  `joinedAt` datetime,
  `updatedAt` datetime,
  `deletedAt` datetime,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Quest` (
  `id` int,
  `postId` int,
  `title` varchar(100),
  `status` enum('activated', 'deactivated'),
  `createdAt` datetime,
  `updatedAt` datetime,
  `deletedAt` datetime,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Post` (
  `id` int,
  `userId` int,
  `questId` int,
  `imageUrl` varchar(100),
  `content` varchar(100),
  `isQuest` int,
  `status` enum('activated', 'deactivated'),
  `createdAt` datetime,
  `updatedAt` datetime,
  `deletedAt` datetime,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `User`(`id`),
  FOREIGN KEY (`questId`) REFERENCES `Quest`(`id`)
);

CREATE TABLE `Comment` (
  `id` int,
  `userId` int,
  `postId` int,
  `content` varchar(100),
  `status` enum('activated', 'deactivated'),
  `createdAt` datetime,
  `updatedAt` datetime,
  `deletedAt` datetime,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `User`(`id`),
  FOREIGN KEY (`postId`) REFERENCES `Post`(`id`)
);

CREATE TABLE `Like` (
  `id` int,
  `postId` int,
  `userId` int,
  `status` enum('activated', 'deactivated'),
  `createdAt` datetime,
  `updatedAt` datetime,
  `deletedAt` datetime,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`userId`) REFERENCES `User`(`id`),
  FOREIGN KEY (`postId`) REFERENCES `Post`(`id`)
);

CREATE TABLE `Hashtag` (
  `id` int,
  `tag` varchar(100),
  `status` enum('activated', 'deactivated'),
  `createdAt` datetime,
  `updatedAt` datetime,
  `deletedAt` datetime,
  PRIMARY KEY (`id`)
);

CREATE TABLE `PostHashtag` (
  `id` int,
  `postId` int,
  `hashtagId` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`postId`) REFERENCES `Post`(`id`),
  FOREIGN KEY (`hashtagId`) REFERENCES `Hashtag`(`id`)
=======
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
>>>>>>> main
);

