import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** Mengelola data mahasiswa, mata kuliah, dan KRS di kampus. */
public class Kampus {
    private String nama;
    private String alamat;
    private ArrayList<Mahasiswa> daftarMahasiswa;
    private ArrayList<MataKuliah> daftarMataKuliah;
    private ArrayList<KRS> daftarKRS;

    /** Konstruktor default. */
    public Kampus() {
        this.nama = "Kampus";
        this.alamat = "Belum diisi";
        this.daftarMahasiswa = new ArrayList<>();
        this.daftarMataKuliah = new ArrayList<>();
        this.daftarKRS = new ArrayList<>();
    }

    /** Konstruktor berparameter. */
    public Kampus(String nama, String alamat) {
        this.daftarMahasiswa = new ArrayList<>();
        this.daftarMataKuliah = new ArrayList<>();
        this.daftarKRS = new ArrayList<>();
        setNama(nama);
        setAlamat(alamat);
    }

    public String getNama() { return this.nama; }
    public String getAlamat() { return this.alamat; }
    public ArrayList<Mahasiswa> getDaftarMahasiswa() { return new ArrayList<>(this.daftarMahasiswa); }
    public ArrayList<MataKuliah> getDaftarMataKuliah() { return new ArrayList<>(this.daftarMataKuliah); }
    public ArrayList<KRS> getDaftarKRS() { return new ArrayList<>(this.daftarKRS); }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) throw new IllegalArgumentException("Nama kampus tidak boleh kosong.");
        this.nama = nama.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) throw new IllegalArgumentException("Alamat kampus tidak boleh kosong.");
        this.alamat = alamat.trim();
    }

    /** Mengganti koleksi mahasiswa dengan salinan yang sudah diperiksa. */
    public void setDaftarMahasiswa(ArrayList<Mahasiswa> daftarMahasiswa) {
        if (daftarMahasiswa == null) throw new IllegalArgumentException("Daftar mahasiswa tidak boleh null.");
        Set<String> nimUnik = new HashSet<>();
        for (Mahasiswa mhs : daftarMahasiswa) {
            if (mhs == null || !nimUnik.add(mhs.getNim().toLowerCase()))
                throw new IllegalArgumentException("Daftar mahasiswa mengandung data null atau NIM duplikat.");
        }
        for (KRS krs : this.daftarKRS) {
            if (krs.getMahasiswa() == null || !nimUnik.contains(krs.getMahasiswa().getNim().toLowerCase()))
                throw new IllegalArgumentException("Daftar tidak boleh menghapus mahasiswa yang masih memiliki KRS.");
        }
        this.daftarMahasiswa = new ArrayList<>(daftarMahasiswa);
    }

    /** Mengganti koleksi mata kuliah dengan salinan yang sudah diperiksa. */
    public void setDaftarMataKuliah(ArrayList<MataKuliah> daftarMataKuliah) {
        if (daftarMataKuliah == null) throw new IllegalArgumentException("Daftar mata kuliah tidak boleh null.");
        Set<String> kodeUnik = new HashSet<>();
        for (MataKuliah mk : daftarMataKuliah) {
            if (mk == null || !kodeUnik.add(mk.getKode().toLowerCase()))
                throw new IllegalArgumentException("Daftar mata kuliah mengandung data null atau kode duplikat.");
        }
        for (KRS krs : this.daftarKRS) {
            for (MataKuliah mk : krs.getDaftarMataKuliah()) {
                if (!kodeUnik.contains(mk.getKode().toLowerCase()))
                    throw new IllegalArgumentException("Daftar tidak boleh menghapus mata kuliah yang sudah tercatat di KRS.");
            }
        }
        this.daftarMataKuliah = new ArrayList<>(daftarMataKuliah);
    }

    /** Mengganti koleksi KRS dengan salinan yang sudah diperiksa. */
    public void setDaftarKRS(ArrayList<KRS> daftarKRS) {
        if (daftarKRS == null) throw new IllegalArgumentException("Daftar KRS tidak boleh null.");
        Set<String> idUnik = new HashSet<>();
        for (KRS krs : daftarKRS) {
            if (krs == null || krs.getMahasiswa() == null
                    || !idUnik.add(krs.getIdKrs().toLowerCase())
                    || this.cariMahasiswa(krs.getMahasiswa().getNim()) == null)
                throw new IllegalArgumentException("Daftar KRS mengandung data tidak valid, ID duplikat, atau mahasiswa belum terdaftar.");
        }
        this.daftarKRS = new ArrayList<>(daftarKRS);
    }

    public boolean tambahMahasiswa(Mahasiswa mhs) {
        if (mhs == null) return false;
        for (Mahasiswa data : this.daftarMahasiswa)
            if (data.getNim().equalsIgnoreCase(mhs.getNim())) return false;
        this.daftarMahasiswa.add(mhs);
        return true;
    }

    public boolean tambahMataKuliah(MataKuliah mk) {
        if (mk == null) return false;
        for (MataKuliah data : this.daftarMataKuliah)
            if (data.getKode().equalsIgnoreCase(mk.getKode())) return false;
        this.daftarMataKuliah.add(mk);
        return true;
    }

    public boolean buatKRS(KRS krs) {
        if (krs == null || krs.getMahasiswa() == null) return false;
        if (this.cariMahasiswa(krs.getMahasiswa().getNim()) == null) return false;
        for (KRS data : this.daftarKRS)
            if (data.getIdKrs().equalsIgnoreCase(krs.getIdKrs())) return false;
        this.daftarKRS.add(krs);
        return true;
    }

    public Mahasiswa cariMahasiswa(String nim) {
        for (Mahasiswa mhs : this.daftarMahasiswa) if (mhs.cocokNim(nim)) return mhs;
        return null;
    }

    public MataKuliah cariMataKuliah(String kode) {
        for (MataKuliah mk : this.daftarMataKuliah) if (mk.cocokKode(kode)) return mk;
        return null;
    }

    public KRS cariKRS(String id) {
        if (id == null) return null;
        for (KRS krs : this.daftarKRS)
            if (krs.getIdKrs().equalsIgnoreCase(id.trim())) return krs;
        return null;
    }

    public void tampilkanMahasiswa() {
        System.out.println("\n=== DAFTAR MAHASISWA ===");
        if (this.daftarMahasiswa.isEmpty()) { System.out.println("Belum ada mahasiswa."); return; }
        for (Mahasiswa mhs : this.daftarMahasiswa) mhs.tampilkanInfo();
    }

    public void tampilkanMataKuliah() {
        System.out.println("\n=== DAFTAR MATA KULIAH ===");
        if (this.daftarMataKuliah.isEmpty()) { System.out.println("Belum ada mata kuliah."); return; }
        for (MataKuliah mk : this.daftarMataKuliah) mk.tampilkanInfo();
    }

    public void tampilkanSemuaKRS() {
        System.out.println("\n=== DAFTAR KRS ===");
        if (this.daftarKRS.isEmpty()) { System.out.println("Belum ada KRS."); return; }
        for (KRS krs : this.daftarKRS) krs.tampilkanKRS();
    }

    public void tampilkanLaporan() {
        System.out.println("\n===== LAPORAN AKADEMIK =====");
        System.out.println("Nama Kampus        : " + this.nama);
        System.out.println("Alamat             : " + this.alamat);
        System.out.println("Jumlah Mahasiswa   : " + this.daftarMahasiswa.size());
        System.out.println("Jumlah Mata Kuliah : " + this.daftarMataKuliah.size());
        System.out.println("Jumlah KRS         : " + this.daftarKRS.size());
        int totalSks = 0;
        for (KRS krs : this.daftarKRS) totalSks += krs.getTotalSks();
        System.out.println("Total SKS seluruh KRS: " + totalSks);
    }
}
