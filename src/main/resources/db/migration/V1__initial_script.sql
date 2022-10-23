DROP SCHEMA IF EXISTS capstone cascade;
CREATE SCHEMA capstone;

CREATE TABLE capstone.USERS (
    user_id uuid,
    username varchar(150),
    password varchar(150),
    user_type varchar(150),
    email varchar(150),
    image_link varchar(150),
    created_date TIMESTAMP WITH TIME ZONE,
    modified_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (user_id)
);

CREATE TABLE capstone.POSTS (
    post_id uuid,
    user_id uuid references capstone.USERS(user_id),
    post_title varchar(150),
    post_description varchar,
    post_category varchar(150),
    image_link varchar(150),
    created_date TIMESTAMP WITH TIME ZONE,
    modified_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (user_id, post_id)
);

CREATE TABLE capstone.COMMENTS(
    comment_id uuid,
    post_id uuid,
    user_id uuid references capstone.USERS(user_id),
    comment varchar,
    image_link varchar(150),
    created_date TIMESTAMP WITH TIME ZONE,
    modified_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (comment_id, post_id, user_id)
);

CREATE TABLE capstone.NEWS(
  news_id uuid,
  news_title varchar(150),
  news_description varchar,
  image_link varchar(150),
  created_date TIMESTAMP WITH TIME ZONE,
  modified_date TIMESTAMP WITH TIME ZONE,
  PRIMARY KEY (news_id)
);

CREATE TABLE capstone.BLOGS(
  blog_id uuid,
  blog_title varchar(150),
  blog_description varchar,
  image_link varchar(150),
  created_date TIMESTAMP WITH TIME ZONE,
  modified_date TIMESTAMP WITH TIME ZONE,
  PRIMARY KEY (blog_id)
);
