import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import dominio.Album;
import dominio.Artista;
import dominio.Musica;

class AudioPlayer {
    boolean isPlaying = false;
    javax.sound.sampled.Clip audioClip;

    public void loadAudio(String filePath) {
        try {
            java.io.File audioFile = new java.io.File(filePath);
            javax.sound.sampled.AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(audioFile);
            audioClip = javax.sound.sampled.AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (Exception e) {
            System.out.println("Erro ao carregar o áudio: " + e.getMessage());
        }
    }

    public void playAudio() {
        if (audioClip != null) {
            audioClip.start();
            isPlaying = true;
        }
    }

    public void stopAudio() {
        if (audioClip != null) {
            audioClip.stop();
            isPlaying = false;
        }
    }
}

public class App {
    private static int currentTrackIndex = 0;

    public static void main(String[] args) throws Exception {
        Musica musica1 = new Musica();
        musica1.setNome("Californication");
        musica1.setDuracao(120);
        musica1.setArquivoAudio("assets/Red-Hot-Chili-Peppers-Californication.wav");
        musica1.setGenero("Rock");

        Musica musica2 = new Musica();
        musica2.setNome("Otherside");
        musica2.setDuracao(120);
        musica2.setArquivoAudio("assets/Red-Hot-Chili-Peppers-Otherside.wav");
        musica2.setGenero("Rock");

        Musica musicaDesama = new Musica();
        musicaDesama.setNome("Como É Que Desama");
        musicaDesama.setDuracao(210);
        musicaDesama.setArquivoAudio("assets/01-Como-é-que-desama.wav");
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
        player.loadAudio(album1.getMusicas().get(currentTrackIndex).getArquivoAudio());

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
                currentTrackIndex = (currentTrackIndex + 1) % album1.getMusicas().size();
                player.stopAudio();
                player.loadAudio(album1.getMusicas().get(currentTrackIndex).getArquivoAudio());
                player.playAudio();
                System.out.println("Próxima música: " + album1.getMusicas().get(currentTrackIndex).getNome());
            }
        });

        JButton previousButton = new JButton("Anterior");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTrackIndex = (currentTrackIndex - 1 + album1.getMusicas().size()) % album1.getMusicas().size();
                player.stopAudio();
                player.loadAudio(album1.getMusicas().get(currentTrackIndex).getArquivoAudio());
                player.playAudio();
                System.out.println("Música anterior: " + album1.getMusicas().get(currentTrackIndex).getNome());
            }
        });

        ImageIcon icon = new ImageIcon("assets/music.png");
        JOptionPane.showOptionDialog(
                null,
                "Tocando agora: " + album1.getMusicas().get(currentTrackIndex).getNome(),
                "PlayMusic",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                icon,
                new Object[]{playStopButton, previousButton, nextButton},
                playStopButton
        );

        if (player.audioClip != null) {
            player.audioClip.close();
        }
    }
}
