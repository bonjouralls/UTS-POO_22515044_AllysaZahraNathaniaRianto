/** Mewakili mata kuliah beserta kapasitas pesertanya. */
public class MataKuliah {
    private String kode;
    private String nama;
    private int sks;
    private int kuota;
    private int jumlahPeserta;

    /** Konstruktor default. */
    public MataKuliah() {
        this.kode = "BELUM-ADA";
        this.nama = "Belum diisi";
        this.sks = 1;
        this.kuota = 1;
        this.jumlahPeserta = 0;
    }

    /** Konstruktor berparameter dengan validasi melalui setter. */
    public MataKuliah(String kode, String nama, int sks, int kuota) {
        this.jumlahPeserta = 0;
        setKode(kode);
        setNama(nama);
        setSks(sks);
        setKuota(kuota);
    }

    public String getKode() { return this.kode; }
    public String getNama() { return this.nama; }
    public int getSks() { return this.sks; }
    public int getKuota() { return this.kuota; }
    public int getJumlahPeserta() { return this.jumlahPeserta; }

    public void setKode(String kode) {
        if (kode == null || kode.trim().isEmpty() || kode.equalsIgnoreCase("BELUM-ADA"))
            throw new IllegalArgumentException("Kode mata kuliah tidak boleh kosong.");
        this.kode = kode.trim();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty() || nama.equalsIgnoreCase("Belum diisi"))
            throw new IllegalArgumentException("Nama mata kuliah tidak boleh kosong.");
        this.nama = nama.trim();
    }

    public void setSks(int sks) {
        if (sks < 1 || sks > 24)
            throw new IllegalArgumentException("SKS harus berada pada rentang 1-24.");
        this.sks = sks;
    }

    public void setKuota(int kuota) {
        if (kuota < 1 || kuota < this.jumlahPeserta)
            throw new IllegalArgumentException("Kuota minimal 1 dan tidak boleh di bawah jumlah peserta.");
        this.kuota = kuota;
    }

    /** Setter terkontrol untuk jumlah peserta; biasanya dikelola oleh KRS. */
    public void setJumlahPeserta(int jumlahPeserta) {
        if (jumlahPeserta < 0 || jumlahPeserta > this.kuota)
            throw new IllegalArgumentException("Jumlah peserta tidak valid atau melebihi kuota.");
        this.jumlahPeserta = jumlahPeserta;
    }

    public boolean tersedia() { return this.jumlahPeserta < this.kuota; }

    public boolean tambahPeserta() {
        if (!tersedia()) return false;
        this.jumlahPeserta++;
        return true;
    }

    public boolean kurangiPeserta() {
        if (this.jumlahPeserta <= 0) return false;
        this.jumlahPeserta--;
        return true;
    }

    public void tampilkanInfo() {
        System.out.println(this.kode + " | " + this.nama + " | " + this.sks
                + " SKS | Peserta: " + this.jumlahPeserta + "/" + this.kuota);
    }

    public boolean cocokKode(String kunci) {
        return kunci != null && this.kode.equalsIgnoreCase(kunci.trim());
    }
}
