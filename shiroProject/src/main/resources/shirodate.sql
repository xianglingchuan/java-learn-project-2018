-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2018 年 12 月 20 日 16:19
-- 服务器版本: 5.5.42
-- PHP 版本: 5.6.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `shirodate`
--

-- --------------------------------------------------------

--
-- 表的结构 `roles_permissions`
--

CREATE TABLE IF NOT EXISTS `roles_permissions` (
  `role_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `permission` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `roles_permissions`
--

INSERT INTO `roles_permissions` (`role_name`, `permission`) VALUES
('admin', 'user:select'),
('user', 'user:create');

-- --------------------------------------------------------

--
-- 表的结构 `test_roles_permissions`
--

CREATE TABLE IF NOT EXISTS `test_roles_permissions` (
  `id` int(11) NOT NULL,
  `roleName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `roles_permissions` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `test_roles_permissions`
--

INSERT INTO `test_roles_permissions` (`id`, `roleName`, `roles_permissions`) VALUES
(1, 'admin', 'admin:list'),
(2, 'user', 'user:list');

-- --------------------------------------------------------

--
-- 表的结构 `test_users`
--

CREATE TABLE IF NOT EXISTS `test_users` (
  `id` int(11) NOT NULL,
  `user_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `test_users`
--

INSERT INTO `test_users` (`id`, `user_name`, `password`) VALUES
(1, 'xlc', 'fced4bf19f8d72a9c8736be99cb1400c');

-- --------------------------------------------------------

--
-- 表的结构 `test_users_roles`
--

CREATE TABLE IF NOT EXISTS `test_users_roles` (
  `id` int(11) NOT NULL,
  `roleName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `user_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `test_users_roles`
--

INSERT INTO `test_users_roles` (`id`, `roleName`, `user_name`) VALUES
(1, 'admin', 'xlc'),
(2, 'user', 'xlc');

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password_salt` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `users`
--

INSERT INTO `users` (`username`, `password`, `password_salt`) VALUES
('Mark', '123456', '123456');

-- --------------------------------------------------------

--
-- 表的结构 `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `role_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `user_roles`
--

INSERT INTO `user_roles` (`role_name`, `username`) VALUES
('admin', 'Mark'),
('user', 'Mark');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
