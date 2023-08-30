-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 09 Jul 2023 pada 11.52
-- Versi server: 10.4.24-MariaDB
-- Versi PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cvagajayacenter`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `databarang`
--

CREATE TABLE `databarang` (
  `idbarang` varchar(30) NOT NULL,
  `idsupplier` varchar(30) NOT NULL,
  `namabarang` varchar(30) NOT NULL,
  `brand` varchar(30) NOT NULL,
  `kualitas` varchar(30) NOT NULL,
  `jenisbarang` varchar(20) NOT NULL,
  `garansi` varchar(20) NOT NULL,
  `jumlahbarang` varchar(30) NOT NULL,
  `harga` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `databarang`
--

INSERT INTO `databarang` (`idbarang`, `idsupplier`, `namabarang`, `brand`, `kualitas`, `jenisbarang`, `garansi`, `jumlahbarang`, `harga`) VALUES
('BR001', 'SP001', 'GTX 1650 TI', 'Brand Sangat Terkenal', 'Kualitas Bagus', 'Vga', '12 Bulan', '80', '2500000'),
('BR002', 'SP002', 'RAM VGEN 16GB', 'Brand Sangat Terkenal', 'Kualitas  standar', 'Ram', '1 Bulan', '30', '300000'),
('BR003', 'SP003', 'RTX 2060 TI', 'Brand Sangat Terkenal', 'Kualitas Bagus', 'Vga', '12 Bulan', '50', '4000000'),
('BR004', 'SP004', 'RAM HYPERX 8 GB', 'Brand Sangat Terkenal', 'Kualitas Sangat Bagus', 'Ram', '1 Bulan', '90', '500000'),
('BR005', 'SP005', 'INTEL CORE I 7 10700F ', 'Brand Sangat Terkenal', 'Kualitas Sangat Bagus', 'Processor', '12 Bulan', '25', '5000000');

-- --------------------------------------------------------

--
-- Struktur dari tabel `datareturn`
--

CREATE TABLE `datareturn` (
  `idreturn` varchar(20) NOT NULL,
  `idtransaksi` varchar(20) NOT NULL,
  `namabarang` varchar(30) NOT NULL,
  `jumlahbarang` varchar(20) NOT NULL,
  `harga` varchar(30) NOT NULL,
  `alasan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `datareturn`
--

INSERT INTO `datareturn` (`idreturn`, `idtransaksi`, `namabarang`, `jumlahbarang`, `harga`, `alasan`) VALUES
('RT001', 'PO001', 'GTX 1650 TI', '20', '50000000', 'karna rusak');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kriteria`
--

CREATE TABLE `kriteria` (
  `kd_kriteria` varchar(30) NOT NULL,
  `nama_kriteria` varchar(30) NOT NULL,
  `prioritas_kepentingan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kriteria`
--

INSERT INTO `kriteria` (`kd_kriteria`, `nama_kriteria`, `prioritas_kepentingan`) VALUES
('K1', 'Harga Sparepart', 'Sangat Penting ke-1'),
('K2', 'Brand', 'Penting ke-2'),
('K3', 'Garansi Sparepart', 'Cukup Penting ke-3'),
('K4', 'Kualitas Sparepart', 'Biasa ke-4');

-- --------------------------------------------------------

--
-- Struktur dari tabel `login`
--

CREATE TABLE `login` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('haycal', '21'),
('admin', 'admin');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `idtransaksi` varchar(30) NOT NULL,
  `tanggal` date NOT NULL,
  `namapembeli` varchar(20) NOT NULL,
  `notel` varchar(13) NOT NULL,
  `kodebarang` varchar(10) NOT NULL,
  `namabarang` varchar(20) NOT NULL,
  `jenisbarang` varchar(20) NOT NULL,
  `jumlahbarang` varchar(20) NOT NULL,
  `garansi` varchar(20) NOT NULL,
  `hargasatuan` varchar(20) NOT NULL,
  `totalharga` varchar(20) NOT NULL,
  `jumlahbayar` varchar(20) NOT NULL,
  `sisabayar` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`idtransaksi`, `tanggal`, `namapembeli`, `notel`, `kodebarang`, `namabarang`, `jenisbarang`, `jumlahbarang`, `garansi`, `hargasatuan`, `totalharga`, `jumlahbayar`, `sisabayar`) VALUES
('PO001', '2023-07-06', 'Haycal rayhansyah', '0817386121', 'BR001', 'GTX 1650 TI', 'Vga', '20', '12 Bulan', '2500000', '50000000', '50000000', '0');

-- --------------------------------------------------------

--
-- Struktur dari tabel `seleksi`
--

CREATE TABLE `seleksi` (
  `idbarang` varchar(20) NOT NULL,
  `namabarang` varchar(50) NOT NULL,
  `total` decimal(4,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `seleksi`
--

INSERT INTO `seleksi` (`idbarang`, `namabarang`, `total`) VALUES
('BR001', 'GTX 1650 TI', '0.97'),
('BR002', 'RAM VGEN 16GB', '0.88'),
('BR003', 'RTX 2060 TI', '0.64'),
('BR004', 'RAM HYPERX 8 GB', '0.91'),
('BR005', 'INTEL CORE I 7 10700F ', '0.66'),
('BR001', 'GTX 1650 TI', '0.97');

-- --------------------------------------------------------

--
-- Struktur dari tabel `sub_kriteria`
--

CREATE TABLE `sub_kriteria` (
  `no_sub` int(11) NOT NULL,
  `kd_kriteria` varchar(30) NOT NULL,
  `nama_kriteria` varchar(30) NOT NULL,
  `nama_sub_kriteria` varchar(30) NOT NULL,
  `prioritas_kepentingan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `sub_kriteria`
--

INSERT INTO `sub_kriteria` (`no_sub`, `kd_kriteria`, `nama_kriteria`, `nama_sub_kriteria`, `prioritas_kepentingan`) VALUES
(1, 'K1', 'Harga Sparepart', 'Harga Rendah', 'Sangat Penting ke-1'),
(2, 'K1', 'Harga Sparepart', 'Harga Standar', 'Penting ke-2'),
(3, 'K1', 'Harga Sparepart', 'Harga Lumayan Tinggi', 'Cukup Penting ke-3'),
(4, 'K1', 'Harga Sparepart', 'Harga Sangat Tinggi', 'Biasa ke-4'),
(5, 'K2', 'Brand', 'Brand Tidak Terkenal', 'Sangat Penting ke-1'),
(6, 'K2', 'Brand', 'Brand Terkenal', 'Cukup Penting ke-2'),
(7, 'K2', 'Brand', 'Brand Sangat Terkenal', 'Biasa ke-3'),
(8, 'K3', 'Garansi Sparepart', '1 bulan', 'Sangat Penting ke-1'),
(9, 'K3', 'Garansi Sparepart', '6 bulan', 'Cukup Penting ke-2'),
(10, 'K3', 'Garansi Sparepart', '12 bulan', 'Biasa ke-3'),
(11, 'K4', 'Kualitas Sparepart', 'Kualitas dibawah standar', 'Sangat Penting ke-1'),
(12, 'K4', 'Kualitas Sparepart', 'Kualitas  standar', 'Penting ke-2'),
(13, 'K4', 'Kualitas Sparepart', 'Kualitas Bagus', 'Cukup Penting ke-3'),
(14, 'K4', 'Kualitas Sparepart', 'Kualitas Sangat Bagus', 'Biasa ke-4');

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `idsupplier` varchar(20) NOT NULL,
  `namasupplier` varchar(20) NOT NULL,
  `namabarang` varchar(25) NOT NULL,
  `jenisbarang` varchar(30) NOT NULL,
  `jumlahbarang` varchar(10) NOT NULL,
  `namapic` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`idsupplier`, `namasupplier`, `namabarang`, `jenisbarang`, `jumlahbarang`, `namapic`) VALUES
('SP001', 'PT nusa 1', 'GTX 1650 TI', 'Vga', '100', 'HABIBI'),
('SP002', 'PT INDAH GEMILANG', 'RAM VGEN 16GB', 'Processor', '30', 'FAJAR'),
('SP003', 'PT COKI COKI', 'RTX 2060 TI', 'Vga', '50', 'FAJAR'),
('SP004', 'PT INDAH GA INDAH', 'RAM HYPERX 8 GB', 'Ram', '90', 'HABIBI'),
('SP005', 'PT MICROSOFT', 'INTEL CORE I 7 10700F ', 'Processor', '25', 'TOPAN');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
