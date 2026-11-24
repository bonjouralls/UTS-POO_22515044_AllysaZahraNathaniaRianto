SISTEM AKADEMIK MINI - VERSI PER KELAS

Kelas:
1. Mahasiswa.java   : data dan perilaku mahasiswa
2. MataKuliah.java  : data dan perilaku mata kuliah serta kuota
3. KRS.java         : transaksi KRS yang menghubungkan Mahasiswa dan MataKuliah
4. Kampus.java      : pengelolaan data akademik
5. AntrianKRS.java  : ADT Queue berbasis circular array (FIFO)
6. Main.java        : menu utama program

Kombinasi NIM yang digunakan pada rancangan ini: 44
Digit terakhir 4 = Akademik Mini.
Digit kedua dari belakang 4; 4 mod 3 = 1 = Queue.

Aturan bisnis:
- Total SKS pada satu KRS maksimal 24.
- Kuota mata kuliah tidak boleh terlampaui.
- NIM, kode mata kuliah, dan ID KRS tidak boleh duplikat pada pengelolaan Kampus.
- Semester mahasiswa berada pada rentang 1 sampai 14.
- KRS harus terhubung dengan mahasiswa yang sudah terdaftar.

Validasi dan enkapsulasi:
- Semua atribut pada class entitas bersifat private.
- Setter yang disediakan memvalidasi nilai dan menggunakan this.
- Konstruktor berparameter menginisialisasi data melalui setter.
- Total SKS dikelola oleh operasi tambah/hapus mata kuliah agar konsisten.
- Getter daftar mengembalikan salinan agar koleksi internal tidak diubah langsung.

Cara menjalankan (JDK terpasang):
1. Buka terminal pada folder yang berisi keenam file .java.
2. Kompilasi: javac *.java
3. Jalankan: java Main
