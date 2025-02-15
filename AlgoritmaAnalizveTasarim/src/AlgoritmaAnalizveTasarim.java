import java.util.*;
import java.util.stream.IntStream;

public class AlgoritmaAnalizveTasarim {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Kullanıcıdan mahalle ve kriter verilerini alma
        System.out.print("Kaç mahalle gireceksiniz? ");
        int mahalleSayisi = scanner.nextInt();
        scanner.nextLine(); // Boşluğu temizleme

        String[] mahalleler = new String[mahalleSayisi];
        int[] nufusYogunlugu = new int[mahalleSayisi];
        int[] ulasimAltyapisi = new int[mahalleSayisi];
        int[] maliyetAnalizi = new int[mahalleSayisi];
        int[] cevreselEtki = new int[mahalleSayisi];
        int[] sosyalFayda = new int[mahalleSayisi];

        for (int i = 0; i < mahalleSayisi; i++) {
            System.out.printf("Mahalle %d adını girin: ", i + 1);
            mahalleler[i] = scanner.nextLine();

            System.out.printf("%s için Nüfus Yoğunluğu: ", mahalleler[i]);
            nufusYogunlugu[i] = scanner.nextInt();

            System.out.printf("%s için Ulaşım Altyapısı: ", mahalleler[i]);
            ulasimAltyapisi[i] = scanner.nextInt();

            System.out.printf("%s için Maliyet Analizi: ", mahalleler[i]);
            maliyetAnalizi[i] = scanner.nextInt();

            System.out.printf("%s için Çevresel Etki: ", mahalleler[i]);
            cevreselEtki[i] = scanner.nextInt();

            System.out.printf("%s için Sosyal Fayda: ", mahalleler[i]);
            sosyalFayda[i] = scanner.nextInt();

            scanner.nextLine(); // Boşluğu temizleme
        }

        // Kriter ağırlıkları
        Map<String, Double> agirliklar = new HashMap<>();
        agirliklar.put("Nüfus Yoğunluğu", 0.35);
        agirliklar.put("Ulaşım Altyapısı", 0.25);
        agirliklar.put("Maliyet Analizi", 0.15);
        agirliklar.put("Çevresel Etki", 0.15);
        agirliklar.put("Sosyal Fayda", 0.1);

        // Normalize edilmiş verileri saklama
        double[][] normalizedData = {
                normalizeData(nufusYogunlugu, true),
                normalizeData(ulasimAltyapisi, true),
                normalizeData(maliyetAnalizi, false),
                normalizeData(cevreselEtki, false),
                normalizeData(sosyalFayda, true)
        };

        // Skor hesaplama
        double[] skorlar = new double[mahalleler.length];
        for (int i = 0; i < mahalleler.length; i++) {
            double toplamSkor = 0;
            int index = 0;
            for (String kriter : agirliklar.keySet()) {
                toplamSkor += normalizedData[index][i] * agirliklar.get(kriter);
                index++;
            }
            skorlar[i] = toplamSkor;
        }

        // En yüksek skoru bulma
        int optimalIndex = IntStream.range(0, skorlar.length)
                .reduce((i, j) -> skorlar[i] > skorlar[j] ? i : j)
                .orElse(-1);

        // Sonuçları yazdırma
        System.out.println("=== Toplu Taşıma Hattı Planlama Sonuçları ===");
        for (int i = 0; i < mahalleler.length; i++) {
            System.out.printf("Mahalle: %s, Skor: %.4f\n", mahalleler[i], skorlar[i]);
        }

        System.out.println("\nEn uygun mahalle:");
        System.out.printf("Mahalle: %s, Skor: %.4f\n", mahalleler[optimalIndex], skorlar[optimalIndex]);
    }

    public static double[] normalizeData(int[] data, boolean maximize) {
        int max = Arrays.stream(data).max().orElse(1);
        int min = Arrays.stream(data).min().orElse(0);
        double[] normalized = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            if (maximize) {
                normalized[i] = (double) (data[i] - min) / (max - min);
            } else {
                normalized[i] = (double) (max - data[i]) / (max - min);
            }
        }
        return normalized;
    }
}
