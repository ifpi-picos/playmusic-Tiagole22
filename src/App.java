import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import dominio.Album;
import dominio.Artista;
import dominio.Musica;

public class App {
    private static int currentTrackIndex = 0;
    private static Musica[] musicas;

    public static void main(String[] args) throws Exception {
        Musica musica1 = new Musica();
        musica1.setNome("Californication");
        musica1.setDuracao(120);
        musica1.setArquivoAudio("./assets/Red-Hot-Chili-Peppers-Californication.wav");
        musica1.setGenero("Rock");

        Musica musica2 = new Musica();
        musica2.setNome("Otherside");
        musica2.setDuracao(120);
        musica2.setArquivoAudio("./assets/Red-Hot-Chili-Peppers-Otherside.wav");
        musica2.setGenero("Rock");

        Musica musicaDesama = new Musica();
        musicaDesama.setNome("Como É Que Desama");
        musicaDesama.setDuracao(210);
        musicaDesama.setArquivoAudio("./assets//home/tiago/Downloads/aula-poo-playmusic-main/assets/01-Como-é-que-desama-Ramon-e-Randinho-part-Rey-Vaqueiro.wav");
        musicaDesama.setGenero("Sertanejo");

        Album album1 = new Album();
        album1.setNome("Primeiro album");
        album1.setAno(2000);
        album1.addMusica(musica1);
        album1.addMusica(musica2);
        album1.addMusica(musicaDesama);

        Artista redHot = new Artista();
        redHot.setNome("Red Hot Chili Peppers");
        redHot.addAlbum(album1);

        System.out.println("Abrindo o PlayMusic");
        AudioPlayer player = new AudioPlayer();
        player.loadAudio(redHot.getAlbuns().get(0).getMusicas().get(currentTrackIndex).getArquivoAudio());

        JButton playStopButton = new JButton("Play");
        playStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.isPlaying) {
                    player.playAudio();
                    playStopButton.setText("Stop");
                } else {
                    player.stopAudio();
                    playStopButton.setText("Play");
                }
            }
        });

        JButton nextButton = new JButton("Próxima");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTrackIndex = (currentTrackIndex + 1) % redHot.getAlbuns().get(0).getMusicas().size();
                player.loadAudio(redHot.getAlbuns().get(0).getMusicas().get(currentTrackIndex).getArquivoAudio());
                player.playAudio(); // Adicionei esta linha
                System.out.println("Próxima música: " + redHot.getAlbuns().get(0).getMusicas().get(currentTrackIndex).getNome());
            }
        });

        JButton previousButton = new JButton("Anterior");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTrackIndex = (currentTrackIndex - 1 + redHot.getAlbuns().get(0).getMusicas().size()) % redHot.getAlbuns().get(0).getMusicas().size();
                player.loadAudio(redHot.getAlbuns().get(0).getMusicas().get(currentTrackIndex).getArquivoAudio());
                player.playAudio(); // Adicionei esta linha
                System.out.println("Música anterior: " + redHot.getAlbuns().get(0).getMusicas().get(currentTrackIndex).getNome());
            }
        });

        ImageIcon icon = new ImageIcon("./assets/music.png");
        
        JOptionPane.showOptionDialog(
                null,
                redHot.getAlbuns().get(0).getMusicas().get(currentTrackIndex).getNome(),
                "PlayMusic",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                icon,
                new Object[] { playStopButton, previousButton, nextButton }, 
               
 playStopButton);

        if (player.audioClip != null) {
            player.audioClip.close();
        }
    }
}
