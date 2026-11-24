import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Mewakili KRS mahasiswa dan daftar mata kuliah yang dipilih. */
public class KRS {
    private String idKrs;
    private Mahasiswa mahasiswa;
    private ArrayList<MataKuliah> daftarMataKuliah;
    private int totalSks;

    /** Konstruktor default. */
    public KRS() {
        this.idKrs = "BELUM-ADA";
        this.mahasiswa = null;
        this.daftarMataKuliah = new ArrayList<>();
        this.totalSks = 0;
    }

    /** Konstruktor berparameter. */
    public KRS(String idKrs, Mahasiswa mahasiswa) {
        this.daftarMataKuliah = new ArrayList<>();
        this.totalSks = 0;
        setIdKrs(idKrs);
        setMahasiswa(mahasiswa);
    }

    public String getIdKrs() { return this.idKrs; }
    public Mahasiswa getMahasiswa() { return this.mahasiswa; }
    public List<MataKuliah> getDaftarMataKuliah() {
        return Collections.unmodifiableList(new ArrayList<>(this.daftarMataKuliah));
    }
    public int getTotalSks() { return this.totalSks; }

    /** Setter total SKS hanya menerima nilai yang sama dengan hasil perhitungan daftar. */
    public void setTotalSks(int totalSks) {
        int hasilHitung = 0;
        for (MataKuliah mk : this.daftarMataKuliah) hasilHitung += mk.getSks();
        if (totalSks < 0 || totalSks > 24 || totalSks != hasilHitung)
            throw new IllegalArgumentException("Total SKS harus sesuai daftar mata kuliah dan maksimal 24.");
        this.totalSks = totalSks;
    }

    public void setIdKrs(String idKrs) {
        if (idKrs == null || idKrs.trim().isEmpty() || idKrs.equalsIgnoreCase("BELUM-ADA"))
            throw new IllegalArgumentException("ID KRS tidak boleh kosong.");
        this.idKrs = idKrs.trim();
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        if (mahasiswa == null) throw new IllegalArgumentException("KRS harus terhubung dengan mahasiswa.");
        this.mahasiswa = mahasiswa;
    }

    /** Mengganti daftar hanya jika KRS masih kosong; penambahan tetap lewat aturan bisnis. */
    public void setDaftarMataKuliah(List<MataKuliah> daftar) {
        if (!this.daftarMataKuliah.isEmpty())
            throw new IllegalStateException("Daftar hanya dapat diatur saat KRS masih kosong.");
        if (daftar == null) throw new IllegalArgumentException("Daftar mata kuliah tidak boleh null.");
        for (MataKuliah mk : daftar) {
            if (mk == null || !this.tambahMataKuliah(mk))
                throw new IllegalArgumentException("Daftar mata kuliah berisi data tidak valid atau melanggar aturan KRS.");
        }
    }

    public boolean tambahMataKuliah(MataKuliah mk) {
        if (mk == null) {
            System.out.println("Mata kuliah tidak valid.");
            return false;
        }
        if (this.totalSks + mk.getSks() > 24) {
            System.out.println("Gagal! Total SKS maksimal 24.");
            return false;
        }
        for (MataKuliah data : this.daftarMataKuliah) {
            if (data.getKode().equalsIgnoreCase(mk.getKode())) {
                System.out.println("Mata kuliah sudah ada di KRS.");
                return false;
            }
        }
        if (!mk.tambahPeserta()) {
            System.out.println("Gagal! Kuota mata kuliah penuh.");
            return false;
        }
        this.daftarMataKuliah.add(mk);
        this.totalSks += mk.getSks();
        return true;
    }

    public boolean hapusMataKuliah(String kode) {
        if (kode == null || kode.trim().isEmpty()) return false;
        for (int i = 0; i < this.daftarMataKuliah.size(); i++) {
            MataKuliah mk = this.daftarMataKuliah.get(i);
            if (mk.getKode().equalsIgnoreCase(kode.trim())) {
                this.totalSks -= mk.getSks();
                mk.kurangiPeserta();
                this.daftarMataKuliah.remove(i);
                return true;
            }
        }
        return false;
    }

    public void tampilkanKRS() {
        System.out.println("\nID KRS: " + this.idKrs);
        if (this.mahasiswa != null) {
            System.out.println("Mahasiswa: " + this.mahasiswa.getNama());
            System.out.println("NIM: " + this.mahasiswa.getNim());
        } else {
            System.out.println("Mahasiswa belum ditentukan.");
        }
        System.out.println("Daftar Mata Kuliah:");
        if (this.daftarMataKuliah.isEmpty()) System.out.println("Belum ada mata kuliah.");
        else for (MataKuliah mk : this.daftarMataKuliah) mk.tampilkanInfo();
        System.out.println("Total SKS: " + this.totalSks);
    }
}
