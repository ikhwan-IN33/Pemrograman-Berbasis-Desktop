package Tugas3;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

abstract class MenuItem {
    protected String nama;
    protected double harga;
    protected String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
    
    public abstract void tampilMenu();
    public String getNama(){
        return nama;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public double getHarga(){
        return harga;
    }
    public void setHarga(double harga){
        this.harga = harga;
    }
    public String getKategori(){
        return kategori;
    }
    public void setKategori(String kategori){
        this.kategori = kategori;
    }
}

class Makanan extends MenuItem {
    private String jenisMakanan;
    public Makanan(String nama, double harga, String kategori, String jenisMakanan){
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }
    @Override
    public void tampilMenu(){
        System.out.println("Makanan: " + nama + " -Harga: Rp" + harga + " -Kategori: " + kategori + " -Jenis: " + jenisMakanan);
    }
    public String getJenisMakanan(){
        return jenisMakanan;
    }
    public void setJenisMakanan(String jenisMakanan){
        this.jenisMakanan = jenisMakanan;
    }
}

class Kondimen extends MenuItem {
    private String jenisKondimen;
    public Kondimen(String nama, double harga, String kategori, String jenisKondimen){
        super(nama, harga, kategori);
        this.jenisKondimen = jenisKondimen;
    }
    @Override
    public void tampilMenu(){
        System.out.println("Kondimen: " + nama + " -Harga: Rp" + harga + " -Kategori: " + kategori + " -Jenis: " + jenisKondimen);
    }
    public String getJenisKondimen(){
        return jenisKondimen;
    }
    public void setJenisKondimen(String jenisMakanan){
        this.jenisKondimen = jenisMakanan;
    }
}

class Minuman extends MenuItem {
    private String jenisMinuman;
    public Minuman (String nama, double harga, String kategori, String jenisMinuman){
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }
    @Override
    public void tampilMenu(){
        System.out.println("Minuman: " + nama + " -Harga: Rp" + harga + " -Kategori: " + kategori + " -Jenis: " + jenisMinuman);
    }
    public String getJenisMinuman(){
        return jenisMinuman;
    }
    public void setJenisMinuman(String jenisMinuman){
        this.jenisMinuman = jenisMinuman;
    }
}

class Diskon extends MenuItem {
    private double diskon;
    public Diskon(String nama, double harga, String kategori, double diskon){
        super(nama, harga, kategori);
        this.diskon = diskon;
    }
    @Override
    public void tampilMenu(){
        System.out.println("Diskon: " + nama + " -Potongan: Rp" + diskon + " -Kategori: " + kategori);
    }
    public double getDiskon(){
        return diskon;
    }
    public void setDiskon(double diskon){
        this.diskon = diskon;
    }
}

class Menu {
    private ArrayList<MenuItem> items;
    public Menu() {
        items = new ArrayList<>();
    }
    public void tambahItem(MenuItem item){
        items.add(item);
    }
    public void tampilMenu(){
        if (items.isEmpty()){
            System.out.println("Menu ksosong.");
        } else {
            for (MenuItem item : items){
                item.tampilMenu();
            }
        }
    }
    public MenuItem getItemByName(String nama) throws Exception {
        for (MenuItem item : items){
            if(item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }        
        }
        throw new Exception("Item tidak ditemukan: " + nama);
    }
    public ArrayList<MenuItem> getItems(){
        return items;
    }
    public void simpanMenuKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter (new FileWriter(namaFile))) {
            for (MenuItem item : items) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    writer.write("Makanan," + m.getNama() + "," + m.getHarga() + "," + m.getKategori() + "," + m.getJenisMakanan());
                }else if (item instanceof Kondimen) {
                    Kondimen m = (Kondimen) item;
                    writer.write("Kondimen," + m.getNama() + "," + m.getHarga() + "," + m.getKategori() + "," + m.getJenisKondimen());
                }  else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    writer.write("Minuman," + m.getNama() + "," + m.getHarga() + "," + m.getKategori() + "," + m.getJenisMinuman());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    writer.write("Diskon," + d.getNama() + "," + d.getHarga() + "," + d.getKategori() + "," + d.getDiskon());
                }
                writer.newLine();
            }
            System.out.println("Menu berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan menu: " + e.getMessage());
        }
    }
    public void muatMenuDariFile(String namaFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String tipe = parts[0];
                String nama = parts[1];
                double harga = Double.parseDouble(parts[2]);
                String kategori = parts[3];
                if (tipe.equals("Makanan")) {
                    String jenis = parts[4];
                    tambahItem(new Makanan(nama, harga, kategori, jenis));
                } else if (tipe.equals("Kondimen")) {
                    String jenis = parts[4];
                    tambahItem (new Kondimen(nama, harga, kategori, jenis));
                } else if (tipe.equals("Minuman")) {
                    String jenis = parts[4];
                    tambahItem (new Minuman(nama, harga, kategori, jenis));
                } else if (tipe.equals("Diskon")) {
                    double diskon = Double.parseDouble(parts[4]);
                    tambahItem(new Diskon(nama, harga, kategori, diskon));
                }
            }
            System.out.println("Menu berhasil dimuat dari file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahaan saat memuat menu: " + e.getMessage());
        }
    }
}

class Pesanan{
    private ArrayList<MenuItem> pesananItems;
    public Pesanan(){
        pesananItems = new ArrayList<>();;
    }
    public void tambahItem(MenuItem item) {
        pesananItems.add(item);
    }
    public double hitungTotal(){
        double total = 0;
        double diskonTotal = 0;
        for (MenuItem item : pesananItems) {
            if (item instanceof Diskon) {
                diskonTotal += ((Diskon) item).getDiskon();
            } else {
                total += item.getHarga();
            }
        }
        return Math.max(0, total - diskonTotal);
    }
    public void tampilStruk() {
        System.out.println("\n=== Struk Pesanan ===");
        if (pesananItems.isEmpty()) {
            System.out.println("Pesanan kosong.");
        } else {
            for (MenuItem item : pesananItems) {
                item.tampilMenu();
            }
            System.out.println("Total Biaya: Rp" + hitungTotal());
        }
    }
    public ArrayList<MenuItem> getPesananItems(){
        return pesananItems;
    }
    public void simpanStrukKeFile(String namaFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter (namaFile))) {
            writer.write("Struk Pesnanan\n");
            for (MenuItem item : pesananItems) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    writer.write("Makanan:" + m.getNama() + " -Rp" + m.getHarga() + " -" + m.getJenisMakanan());
                } else if (item instanceof Kondimen) {
                    Kondimen m = (Kondimen) item;
                    writer.write("Kondimen: " + m.getNama() + " -Rp" + m.getHarga() + " -" + m.getJenisKondimen());
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    writer.write("Minuman: " + m.getNama() + "- Rp" + m.getHarga() + " -" + m.getJenisMinuman());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    writer.write("Diskon: " + d.getNama() + " -Potongan (persen)" + d.getDiskon()); 
                }
                writer.newLine();
            }
            writer.write("Total: Rp" + hitungTotal());
            System.out.println("Struk berhasil disimpan ke file:" + namaFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan struk: " + e.getMessage());
        }
    }
}

public class Tugas3 {
    public static void main (String[] agrs) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        Pesanan pesanan = new Pesanan();
        menu.muatMenuDariFile("Menu.txt");
        boolean running = true;
        while (running) {
            System.out.println("\n=== Selamat Datang di Pecel Lele Cak Mimin ===");
            System.out.println("1. Tambah Item ke Menu");;
            System.out.println("2. Tampilkan Menu Restoran");
            System.out.println("3. Terima Pesanan Pelanggan");
            System.out.println("4. Hitung dan Tampilkan Struk Pesanan");
            System.out.println("5. Simpan Menu ke File");
            System.out.println("6. Keluar");
            System.out.print("Silakan pilih opsi (1-6): ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();
            switch(pilihan) {
                case 1:
                    System.out.print("Jenis Item (Makanan/Kondimen/Minuman/Diskon): ");
                    String jenis = scanner.nextLine();
                    System.out.print("Nama: ");
                    String nama = scanner.nextLine();
                    System.out.print("Harga: ");
                    double harga = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Kategori: ");
                    String kategori = scanner.nextLine();
                    if (jenis.equalsIgnoreCase("Makanan")) {
                        System.out.print("Jenis Makanan: ");
                        String jenisMakanan = scanner.nextLine();
                        menu.tambahItem(new Makanan(nama, harga, kategori, jenisMakanan));
                    } else if (jenis.equalsIgnoreCase("Kondimen")) {
                        System.out.print("Jenis Kondimen: ");
                        String jenisKondimen = scanner.nextLine();
                        menu.tambahItem(new Kondimen(nama, harga, kategori, jenisKondimen));
                    } else if (jenis.equalsIgnoreCase("Minuman")) {
                        System.out.print("Jenis Minuman: ");
                        String jenisMinuman = scanner.nextLine();
                        menu.tambahItem(new Minuman(nama, harga, kategori, jenisMinuman));
                    } else if (jenis.equalsIgnoreCase("Diskon")) {
                        System.out.print("Jumlah Diskon: ");
                        double diskon = scanner.nextDouble();
                        menu.tambahItem(new Diskon(nama, harga, kategori, diskon));
                    } else {
                        System.out.println("Jenis item tidak valid.");
                    }
                    break;

                case 2:
                    menu.tampilMenu();
                    break;
                
                case 3:
                    System.out.println("Masukan nama item yang dipesan (ketik'selesai' untuk berhenti): ");
                    while (true) {
                        System.out.print("Nama Item: ");
                        String namaItem = scanner.nextLine();
                        if (namaItem.equalsIgnoreCase("selesai")){
                            break;
                        }
                        try {
                            MenuItem item = menu.getItemByName(namaItem);
                            pesanan.tambahItem(item);
                            System.out.println("Item ditambahkan ke pesanan.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    break;
                case 4:
                    pesanan.tampilStruk();
                    pesanan.simpanStrukKeFile("StrukPesanan.txt");
                    break;
                case 5:
                    menu.simpanMenuKeFile("Menu.txt");
                    break;
                case 6:
                    running = false;
                    System.out.println("Terima kasih telah berkunjung!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        scanner.close();
    }
}