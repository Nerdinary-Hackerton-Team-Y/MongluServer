USE mongle;

INSERT INTO user (username, nickname, password)
VALUES ("mongle1", "눈사람 장인1", "asdf1234");
INSERT INTO user (username, nickname, password)
VALUES ("mongle2", "눈사람 장인2", "asdf1234");
INSERT INTO user (username, nickname, password)
VALUES ("mongle3", "눈사람 장인3", "asdf1234");

INSERT INTO quest (title)
VALUES ("고양이 눈사람 만들기");

INSERT INTO post (user_id, quest_id, title, content, is_quest, `score`)
VALUES (1, 1, "샴고양이 눈사람", "귀여워", 1, 2);
INSERT INTO post (user_id, quest_id, title, content, is_quest, `score`)
VALUES (1, 1, "길냥이 눈사람", "귀여워", 1, 1);
INSERT INTO post (user_id, quest_id, title, content, is_quest, `score`)
VALUES (1, 1, "그냥 고양이 눈사람", "귀여워", 1, 3);
INSERT INTO post (user_id, quest_id, title, content, is_quest, `score`)
VALUES (1, 1, "못만든 눈사람", "안귀여워", 1, 0);

INSERT INTO like_history (post_id, user_id)
VALUES (1, 1);
INSERT INTO like_history (post_id, user_id)
VALUES (1, 2);
INSERT INTO like_history (post_id, user_id)
VALUES (2, 1);
INSERT INTO like_history (post_id, user_id)
VALUES (3, 1);
INSERT INTO like_history (post_id, user_id)
VALUES (3, 2);
INSERT INTO like_history (post_id, user_id)
VALUES (3, 3);
