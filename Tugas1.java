package Tugas1;

import java.util.ArrayList;
import java.util.Scanner;

class MenuItem {
    String nama;
    int harga;
    String kategori;

    public MenuItem (String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
    @Override
    public String toString(){
        return nama + " - Rp" + harga + " (" + kategori + ") ";
    }

}

class OrderItem {
    MenuItem item;
    int jumlah;

    public OrderItem (MenuItem item, int jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }
}

public class Tugas1 {
    public static void main(String[] args) {
        //inisiasi menu restoran
        ArrayList<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem("Nasi + Sayap", 15000,"makanan"));
        menu.add(new MenuItem("Nasi + Paha bawah", 18000, "makanan"));
        menu.add(new MenuItem("Nasi + Dada", 25000, "makanan"));
        menu.add(new MenuItem("Nasi + Paha atas", 25000, "makanan"));
        menu.add(new MenuItem("Sayap", 12000, "alacarte"));
        menu.add(new MenuItem("Paha Bawah", 14000, "alacarte"));
        menu.add(new MenuItem("Dada", 20000, "alacarte"));
        menu.add(new MenuItem("Dada Lembut", 17000, "alacarte"));
        menu.add(new MenuItem("Paha Atas", 20000, "alacarte"));
        menu.add(new MenuItem("Nasi", 7000, "side item"));
        menu.add(new MenuItem("Beef Burger", 16000, "side item"));
        menu.add(new MenuItem("Cheese Burger", 20000, "side item"));
        menu.add(new MenuItem("Sambal Bawang", 7000, "side item"));
        menu.add(new MenuItem("Saus Keju", 7000, "side item"));
        menu.add(new MenuItem("Kentang Goreng", 13000, "side item"));
        menu.add(new MenuItem("Spageti", 13000, "side item"));
        menu.add(new MenuItem("Air Mineral", 7000, "minuman"));
        menu.add(new MenuItem("Es Teh", 7000, "minuman"));
        menu.add(new MenuItem("Es Jeruk", 8000, "minuman,"));
        menu.add(new MenuItem("Milo", 10000, "minuman"));

        Scanner sc = new Scanner(System.in);

        System.out.println("\n === Menu Restoran ===");
        for(int i=0; i < menu.size(); i++) {
            System.out.println((i+1)+ ". " + menu.get(i));
        }
        
        ArrayList<OrderItem> pesanan = new ArrayList<>();
        System.out.println("\n Masukan pesanan Anda. Format : Nama menu = Jumlah");
        System.out.println("Ketik 'selesai' untuk menyelesaikan pesanan.");
        while(true){
            System.out.print("Pesanan: ");
            String input = sc.nextLine();
            if(input.equalsIgnoreCase("Selesai")){
                break;
            }
            String[] parts = input.split("=");
            if(parts.length == 2) {
                String nama = parts[0].trim();
                int jumlah = Integer.parseInt(parts[1].trim());
                MenuItem item = null;
                for (MenuItem m : menu){
                    if (m.nama.equalsIgnoreCase(nama)) {
                        item = m;
                        break;
                    }
                }
                if (item !=null && jumlah > 0){
                    pesanan.add(new OrderItem(item, jumlah));
                } else {
                    System.out.println("Menu tidak ditemukan atau jumlah tidak valid");
                }
            } else {
                System.out.println("Format salah. Gunakan: Nama Menu = Jumlah");
            }
        }
        if (pesanan.isEmpty()) {
            System.out.println("Tidak ada pesanan. Program berakhir.");
            return;
        }

        int subtotal = 0;
        for (OrderItem o : pesanan) {
            subtotal += o.item.harga * o.jumlah;
        }

        int diskonMinuman = 0;
        if (subtotal > 50000) {
            int totalMinuman = 0;
            int hargaMinuman = Integer.MAX_VALUE;
            for (OrderItem o : pesanan) {
                if (o.item.kategori.equals("minuman")) {
                    totalMinuman += o.jumlah;
                    if(o.item.harga < hargaMinuman) {
                        hargaMinuman = o.item.harga;
                    }
                }
            }
            if (totalMinuman >= 2) {
                int gratis = totalMinuman/2;
                diskonMinuman = hargaMinuman*gratis;
            }
        }

        int subtotalSetelalhDsikonMinuman = subtotal - diskonMinuman;

        double diskonPersen = 0;
        if(subtotalSetelalhDsikonMinuman > 100000) {
            diskonPersen = subtotalSetelalhDsikonMinuman * 0.1;
        }

        double subtotalAkhir = subtotalSetelalhDsikonMinuman - diskonPersen;

        double pajak = subtotalAkhir * 0.1;

        int biayaPelayanan = 20000;

        double total = subtotalAkhir + pajak + biayaPelayanan;

        System.out.println("\n === Struk Pesanan ===");
        for (OrderItem o : pesanan) {
            int totalPerItem = o.item.harga * o.jumlah;
            System.out.println(o.item.nama + " x" + o.jumlah + " = Rp " + totalPerItem);
        }
        System.out.println("Subtotal: Rp " +subtotal);
        if (diskonMinuman > 0) {
            System.out.println("Diskon Minuman (Beli 1 Gratis 1): Rp " + diskonMinuman);
        }
        if (diskonPersen > 0) {
            System.out.println("Diskon 10%: Rp " + diskonPersen);
        }
        System.out.println("Subtotal setelah diskon: Rp " + (int) subtotalAkhir);
        System.out.println("Pajak (10%): Rp " + (int) pajak);
        System.out.println("Biaya Pelayanan: Rp " + biayaPelayanan);
        System.out.println("Total Biaya: Rp " + (int) total);
        System.out.println("Terima kasih sudah memesan");
    }
    
}