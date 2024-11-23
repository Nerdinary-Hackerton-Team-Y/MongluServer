DROP DATABASE IF EXISTS mongle;
CREATE DATABASE IF NOT EXISTS mongle;
USE mongle;

CREATE TABLE `User` (
                        `id` int,
                        `name` varchar(100),
                        `username` varchar(100),
                        `nickname` varchar(100),
                        `password` varchar(100),
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
                        `title` varchar(100),
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
);

