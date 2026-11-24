/** ADT Queue berbasis circular array untuk antrean pemrosesan KRS (FIFO). */
public class AntrianKRS {
    private KRS[] data;
    private int kapasitas;
    private int depan;
    private int belakang;
    private int jumlah;

    /** Konstruktor dengan kapasitas yang ditentukan. */
    public AntrianKRS(int kapasitas) {
        if (kapasitas <= 0) throw new IllegalArgumentException("Kapasitas harus lebih dari 0.");
        this.kapasitas = kapasitas;
        this.data = new KRS[kapasitas];
        this.depan = 0;
        this.belakang = -1;
        this.jumlah = 0;
    }

    public boolean isEmpty() { return this.jumlah == 0; }
    public boolean isFull() { return this.jumlah == this.kapasitas; }
    public int size() { return this.jumlah; }

    public boolean enqueue(KRS krs) {
        if (krs == null) { System.out.println("Data KRS tidak valid."); return false; }
        if (this.isFull()) { System.out.println("Antrian penuh!"); return false; }
        this.belakang = (this.belakang + 1) % this.kapasitas;
        this.data[this.belakang] = krs;
        this.jumlah++;
        System.out.println("KRS berhasil masuk antrian.");
        return true;
    }

    public KRS dequeue() {
        if (this.isEmpty()) { System.out.println("Antrian kosong!"); return null; }
        KRS hasil = this.data[this.depan];
        this.data[this.depan] = null;
        this.depan = (this.depan + 1) % this.kapasitas;
        this.jumlah--;
        if (this.jumlah == 0) { this.depan = 0; this.belakang = -1; }
        return hasil;
    }

    public KRS front() {
        if (this.isEmpty()) { System.out.println("Antrian kosong!"); return null; }
        return this.data[this.depan];
    }

    public void tampilkan() {
        System.out.println("\n=== ISI ANTRIAN KRS ===");
        if (this.isEmpty()) { System.out.println("Antrian masih kosong."); return; }
        for (int i = 0; i < this.jumlah; i++) {
            int indeks = (this.depan + i) % this.kapasitas;
            KRS krs = this.data[indeks];
            System.out.println((i + 1) + ". " + krs.getIdKrs() + " - " + krs.getMahasiswa().getNama());
        }
        System.out.println("Jumlah antrian: " + this.size());
    }
}
