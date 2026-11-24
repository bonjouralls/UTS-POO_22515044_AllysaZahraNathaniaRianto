import java.util.Scanner;

/** Menu utama Sistem Akademik Mini. */
public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final Kampus kampus = new Kampus("Akademik Mini", "Indonesia");
    private static final AntrianKRS antrian = new AntrianKRS(5);

    private static int bacaAngka(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                int angka = Integer.parseInt(input.nextLine().trim());
                if (angka < 0) { System.out.println("Angka tidak boleh negatif."); continue; }
                return angka;
            } catch (NumberFormatException e) { System.out.println("Input harus berupa bilangan bulat."); }
        }
    }

    private static String bacaTeks(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();
            if (!teks.isEmpty()) return teks;
            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static void menuTambahMahasiswa() {
        System.out.println("\n=== TAMBAH MAHASISWA ===");
        try {
            String nim = bacaTeks("NIM: ");
            String nama = bacaTeks("Nama: ");
            String prodi = bacaTeks("Program Studi: ");
            int semester;
            do { semester = bacaAngka("Semester (1-14): "); }
            while (semester < 1 || semester > 14);
            if (kampus.tambahMahasiswa(new Mahasiswa(nim, nama, prodi, semester)))
                System.out.println("Mahasiswa berhasil ditambahkan.");
            else System.out.println("Gagal: NIM sudah terdaftar.");
        } catch (IllegalArgumentException e) { System.out.println("Data tidak valid: " + e.getMessage()); }
    }

    private static void menuTambahMataKuliah() {
        System.out.println("\n=== TAMBAH MATA KULIAH ===");
        try {
            String kode = bacaTeks("Kode Mata Kuliah: ");
            String nama = bacaTeks("Nama Mata Kuliah: ");
            int sks;
            do { sks = bacaAngka("Jumlah SKS (1-24): "); }
            while (sks < 1 || sks > 24);
            int kuota;
            do { kuota = bacaAngka("Kuota Mahasiswa (minimal 1): "); }
            while (kuota < 1);
            if (kampus.tambahMataKuliah(new MataKuliah(kode, nama, sks, kuota)))
                System.out.println("Mata kuliah berhasil ditambahkan.");
            else System.out.println("Gagal: kode mata kuliah sudah terdaftar.");
        } catch (IllegalArgumentException e) { System.out.println("Data tidak valid: " + e.getMessage()); }
    }

    private static void menuBuatKRS() {
        System.out.println("\n=== MEMBUAT KRS ===");
        String nim = bacaTeks("Masukkan NIM: ");
        Mahasiswa mhs = kampus.cariMahasiswa(nim);
        if (mhs == null) { System.out.println("Mahasiswa tidak ditemukan."); return; }
        String id = bacaTeks("ID KRS: ");
        try {
            if (kampus.buatKRS(new KRS(id, mhs))) System.out.println("KRS berhasil dibuat.");
            else System.out.println("Gagal membuat KRS: ID sudah digunakan atau mahasiswa tidak terdaftar.");
        } catch (IllegalArgumentException e) { System.out.println("Data tidak valid: " + e.getMessage()); }
    }

    private static void menuTambahMatkulKRS() {
        System.out.println("\n=== TAMBAH MATA KULIAH KE KRS ===");
        KRS krs = kampus.cariKRS(bacaTeks("Masukkan ID KRS: "));
        if (krs == null) { System.out.println("KRS tidak ditemukan."); return; }
        MataKuliah mk = kampus.cariMataKuliah(bacaTeks("Kode Mata Kuliah: "));
        if (mk == null) { System.out.println("Mata kuliah tidak ditemukan."); return; }
        krs.tambahMataKuliah(mk);
    }

    private static void menuCariData() {
        System.out.println("\n1. Cari Mahasiswa\n2. Cari Mata Kuliah");
        int pilih = bacaAngka("Pilih: ");
        if (pilih == 1) {
            Mahasiswa mhs = kampus.cariMahasiswa(bacaTeks("Masukkan NIM: "));
            if (mhs == null) System.out.println("Mahasiswa tidak ditemukan."); else mhs.tampilkanInfo();
        } else if (pilih == 2) {
            MataKuliah mk = kampus.cariMataKuliah(bacaTeks("Masukkan kode mata kuliah: "));
            if (mk == null) System.out.println("Mata kuliah tidak ditemukan."); else mk.tampilkanInfo();
        } else System.out.println("Pilihan tidak valid.");
    }

    private static void menuAntrian() {
        int pilih;
        do {
            System.out.println("\n===== MENU ANTRIAN KRS =====");
            System.out.println("1. Enqueue (Tambah Antrian)\n2. Dequeue (Proses Antrian)\n3. Front (Lihat Depan)\n4. Tampilkan Antrian\n5. Cek Kosong\n6. Cek Penuh\n7. Jumlah Antrian\n0. Kembali");
            pilih = bacaAngka("Pilih: ");
            switch (pilih) {
                case 1: {
                    KRS krs = kampus.cariKRS(bacaTeks("Masukkan ID KRS: "));
                    if (krs == null) System.out.println("KRS tidak ditemukan."); else antrian.enqueue(krs);
                    break;
                }
                case 2: {
                    KRS hasil = antrian.dequeue();
                    if (hasil != null) System.out.println("KRS diproses: " + hasil.getIdKrs());
                    break;
                }
                case 3: {
                    KRS hasil = antrian.front();
                    if (hasil != null) System.out.println("Antrian depan: " + hasil.getIdKrs());
                    break;
                }
                case 4: antrian.tampilkan(); break;
                case 5: System.out.println(antrian.isEmpty() ? "Antrian kosong." : "Antrian tidak kosong."); break;
                case 6: System.out.println(antrian.isFull() ? "Antrian penuh." : "Antrian belum penuh."); break;
                case 7: System.out.println("Jumlah antrian: " + antrian.size()); break;
                case 0: System.out.println("Kembali ke menu utama."); break;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (pilih != 0);
    }

    public static void main(String[] args) {
        int pilih;
        do {
            System.out.println("\n================================\n       SISTEM AKADEMIK MINI\n================================");
            System.out.println("1. Tambah Mahasiswa\n2. Tambah Mata Kuliah\n3. Buat KRS\n4. Tambah Mata Kuliah ke KRS\n5. Tampilkan Data Mahasiswa\n6. Tampilkan Data Mata Kuliah\n7. Tampilkan Seluruh KRS\n8. Cari Data\n9. Menu ADT Queue\n10. Laporan Akademik\n0. Keluar");
            pilih = bacaAngka("Masukkan pilihan: ");
            switch (pilih) {
                case 1: menuTambahMahasiswa(); break;
                case 2: menuTambahMataKuliah(); break;
                case 3: menuBuatKRS(); break;
                case 4: menuTambahMatkulKRS(); break;
                case 5: kampus.tampilkanMahasiswa(); break;
                case 6: kampus.tampilkanMataKuliah(); break;
                case 7: kampus.tampilkanSemuaKRS(); break;
                case 8: menuCariData(); break;
                case 9: menuAntrian(); break;
                case 10: kampus.tampilkanLaporan(); break;
                case 0: System.out.println("Terima kasih telah menggunakan program."); break;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (pilih != 0);
        input.close();
    }
}
