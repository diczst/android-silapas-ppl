-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 17, 2022 at 02:23 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_lapas`
--

-- --------------------------------------------------------

--
-- Table structure for table `antrian`
--

CREATE TABLE `antrian` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `kunjungan_id` bigint(20) UNSIGNED DEFAULT NULL,
  `no_antrian` int(11) NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kunjungan`
--

CREATE TABLE `kunjungan` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `binaan_id` bigint(20) UNSIGNED NOT NULL,
  `pengunjung_id` bigint(20) UNSIGNED NOT NULL,
  `hub_keluarga` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hari` datetime NOT NULL,
  `keterangan` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bukti_vaksin` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(3, '2022_09_14_121452_create_pengunjung_table', 1),
(8, '2022_09_17_113228_create_wargabinaan_table', 2),
(9, '2022_09_17_114158_create_kunjungan_table', 2),
(10, '2022_09_17_122047_create_antrian_table', 2);

-- --------------------------------------------------------

--
-- Table structure for table `pengunjung`
--

CREATE TABLE `pengunjung` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nik` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `no_handphone` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `jenkel` tinyint(1) NOT NULL,
  `alamat` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pengunjung`
--

INSERT INTO `pengunjung` (`id`, `nik`, `nama`, `no_handphone`, `jenkel`, `alamat`, `password`, `image`) VALUES
(1, '1771923829300381', 'Heimann Schruze', '089528392833', 0, 'Jl. In Aja dulu', '12345678', 'test.jpg'),
(3, '17719238293023281', 'Heimann Heidegger', '0895283928223', 0, 'Jl. In Aja dulu', '$2y$10$qKl7WYgZwhVfUSG/gVW1lusma7DTRBFfkPK4maYSUDmGurtkOr3dK', 'test.jpg'),
(4, '1771089239293', 'Wolfgang Grimmer', '089238928392', 0, 'Jl in aja dlu', '$2y$10$az34TAAsZhuBtfxhQn.IuOlqh37kRBBAeNjMOvqJDVfmE8V7k2IXG', '1717133_undraw_Certificate_re_yadi(1).png'),
(5, '17710892222222', 'Wolfgang Johann', '0892389283434', 0, 'Jl in aja dlu', '$2y$10$bEei2V6r5DjpqjBSlEZTv.dXkWQruKDqIgyTw0qRX6o4z8HVB4c3q', '1244269_Untitled-1.png'),
(6, 'asda', 'asdasd', 'asdasd', 1, 'dd', '$2y$10$uE0/H6v7FxIGO/pmngto6O2JBqFxsJ9HnMvPtrU1U7EjH/cuElys2', '1443577_1663339597788_croppedImg.jpg'),
(7, '1771089233333', 'Edward Wagner', '1234565421', 0, 'Jl. In Aja dulu', '$2y$10$XDVt4NuO5YAYV0oEBHJ91urM5N0s3HOJo10LyAqANSGkGMS7Z3xze', '1401417_1663340336590_croppedImg.jpg'),
(8, '17710892392393', 'asdad', '223232', 1, 'wqeq', '$2y$10$OQSWz19KN0gPXPgSyOHHz.MN792uO4Q2s0tQmmZlffgchknjOh6t6', '1521790_1663341245211_croppedImg.jpg'),
(9, '17710893443245231', 'Dicky Pratama', '0895412791504', 0, 'Jl. Danau, Kel. Dusun Besar, Kec. Singaran Pati', '$2y$10$d.hlarG654utOK1knPj.eeXuejmur1A9UCPIf.5PnQ4qljeI/Ybxq', '0328265_1663386293454_croppedImg.jpg'),
(10, '17710812345678', 'Dicky Pratama', '08234567891', 1, 'Jl Danau', '$2y$10$KO9PKa6166Mth2oUUR1JYOQHNs07XDCen4Ovfq./VZBj7bLuNkxF6', '0304775_1663386651334_croppedImg.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `abilities` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wargabinaan`
--

CREATE TABLE `wargabinaan` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `pengunjung_id` bigint(20) UNSIGNED DEFAULT NULL,
  `nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `kategori` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lama_pidana` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `jenis_kejahatan` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tanggal_mulai` date DEFAULT NULL,
  `tanggal_berakhir` date DEFAULT NULL,
  `tanggal_pengajuan` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `antrian`
--
ALTER TABLE `antrian`
  ADD PRIMARY KEY (`id`),
  ADD KEY `antrian_kunjungan_id_foreign` (`kunjungan_id`);

--
-- Indexes for table `kunjungan`
--
ALTER TABLE `kunjungan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kunjungan_binaan_id_foreign` (`binaan_id`),
  ADD KEY `kunjungan_pengunjung_id_foreign` (`pengunjung_id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengunjung`
--
ALTER TABLE `pengunjung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `pengunjung_nik_unique` (`nik`),
  ADD UNIQUE KEY `pengunjung_no_handphone_unique` (`no_handphone`);

--
-- Indexes for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- Indexes for table `wargabinaan`
--
ALTER TABLE `wargabinaan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `wargabinaan_pengunjung_id_foreign` (`pengunjung_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `antrian`
--
ALTER TABLE `antrian`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `kunjungan`
--
ALTER TABLE `kunjungan`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `pengunjung`
--
ALTER TABLE `pengunjung`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wargabinaan`
--
ALTER TABLE `wargabinaan`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `antrian`
--
ALTER TABLE `antrian`
  ADD CONSTRAINT `antrian_kunjungan_id_foreign` FOREIGN KEY (`kunjungan_id`) REFERENCES `kunjungan` (`id`);

--
-- Constraints for table `kunjungan`
--
ALTER TABLE `kunjungan`
  ADD CONSTRAINT `kunjungan_binaan_id_foreign` FOREIGN KEY (`binaan_id`) REFERENCES `wargabinaan` (`id`),
  ADD CONSTRAINT `kunjungan_pengunjung_id_foreign` FOREIGN KEY (`pengunjung_id`) REFERENCES `pengunjung` (`id`);

--
-- Constraints for table `wargabinaan`
--
ALTER TABLE `wargabinaan`
  ADD CONSTRAINT `wargabinaan_pengunjung_id_foreign` FOREIGN KEY (`pengunjung_id`) REFERENCES `pengunjung` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
