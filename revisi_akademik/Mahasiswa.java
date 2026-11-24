/** Mewakili data mahasiswa pada Sistem Akademik Mini. */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String prodi;
    private int semester;

    /** Konstruktor default. */
    public Mahasiswa() {
        this.nim = "BELUM-ADA";
        this.nama = "Belum diisi";
        this.prodi = "Belum diisi";
        this.semester = 1;
    }

    /** Konstruktor berparameter; seluruh data divalidasi melalui setter. */
    public Mahasiswa(String nim, String nama, String prodi, int semester) {
        setNim(nim);
        setNama(nama);
        setProdi(prodi);
        setSemester(semester);
    }

    public String getNim() { return this.nim; }
    public String getNama() { return this.nama; }
    public String getProdi() { return this.prodi; }
    public int getSemester() { return this.semester; }

    public void setNim(String nim) {
        if (nim == null || nim.trim().isEmpty() || nim.equalsIgnoreCase("BELUM-ADA"))
            throw new IllegalArgumentException("NIM tidak boleh kosong atau menggunakan nilai default.");
        this.nim = nim.trim();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty() || nama.equalsIgnoreCase("Belum diisi"))
            throw new IllegalArgumentException("Nama mahasiswa tidak boleh kosong.");
        this.nama = nama.trim();
    }

    public void setProdi(String prodi) {
        if (prodi == null || prodi.trim().isEmpty() || prodi.equalsIgnoreCase("Belum diisi"))
            throw new IllegalArgumentException("Program studi tidak boleh kosong.");
        this.prodi = prodi.trim();
    }

    public void setSemester(int semester) {
        if (semester < 1 || semester > 14)
            throw new IllegalArgumentException("Semester harus berada pada rentang 1-14.");
        this.semester = semester;
    }

    public void tampilkanInfo() {
        System.out.println("NIM: " + this.nim + " | Nama: " + this.nama
                + " | Prodi: " + this.prodi + " | Semester: " + this.semester);
    }

    public boolean naikSemester() {
        if (this.semester >= 14) return false;
        this.setSemester(this.semester + 1);
        return true;
    }

    public boolean cocokNim(String kunci) {
        return kunci != null && this.nim.equalsIgnoreCase(kunci.trim());
    }
}
