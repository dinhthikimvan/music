package com.example.cuoiky;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    TextView txtTitle, txtTimesong, txtTimeToTal;
    SeekBar sksong;
    ImageView imgHinh;
    ImageButton btnPrev, btnStop, btnPlay, btnNext;

    ArrayList<song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Anhxa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotale);
        khoitaoMediaPlayer();


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0 ){
                    position= arraySong.size() -1;
                }
                if (mediaPlayer.isPlaying())    {
                    mediaPlayer.stop();
                }
                khoitaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                SetTimeTotal();
                UpdateTimesong();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position > arraySong.size() - 1){
                    position=0;
                }
                if (mediaPlayer.isPlaying())    {
                    mediaPlayer.stop();
                }
                khoitaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                SetTimeTotal();
                UpdateTimesong();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                khoitaoMediaPlayer();

            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    //nếu đang hát thì mình cho nó pause - đổi hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(android.R.drawable.ic_media_play);
                }else {
                    //đang ngưng - phát - đổi hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                }
                SetTimeTotal();
                UpdateTimesong();
                imgHinh.startAnimation(animation);

            }
        });
        sksong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sksong.getProgress());

            }
        });
    }



    private void UpdateTimesong(){
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimesong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                //update progrees sksong
                sksong.setProgress(mediaPlayer.getCurrentPosition());
                //kiểm tra thời gian bài hát - nếu kết thúc -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;
                        if (position > arraySong.size() - 1){
                            position=0;
                        }
                        if (mediaPlayer.isPlaying())    {
                            mediaPlayer.stop();
                        }
                        khoitaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                        SetTimeTotal();
                        UpdateTimesong();
                    }
                });
                handler.postDelayed(this,500);

            }
        }, 100);
    }



    private void SetTimeTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeToTal.setText(dinhDangGio.format(mediaPlayer.getDuration())  + "");
        //gán max của sksong = mediaPlayer.getDuration()
        sksong.setMax(mediaPlayer.getDuration());
    }

    private void khoitaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(HomeActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new song("Bài hát: Anh đã quen với cô đơn", R.raw.anh_da_quen_voi_codon));
        arraySong.add(new song("Bài hát: Tháng Năm", R.raw.thang_nam));
        arraySong.add(new song("Bài hát: Anh nhà ở đâu thế ", R.raw.anhnhaodauthe));
        arraySong.add(new song("Bài hát: 3107_7", R.raw.ba_mot_khong_bay_b));
        arraySong.add(new song("Bài hát: Thích rồi đấy", R.raw.thich_roi_day));
    }

    private void Anhxa() {
        txtTimesong    = (TextView) findViewById(R.id.textviewtimsong);
        txtTimeToTal   = (TextView) findViewById(R.id.textviewtotal);
        txtTitle       =(TextView) findViewById(R.id.txtTitle);
        sksong         = (SeekBar) findViewById(R.id.seeBarsong);
        btnNext        = (ImageButton) findViewById(R.id.imageButtonprevnext);
        btnPlay        = (ImageButton) findViewById(R.id.imageButtonplay);
        btnPrev        = (ImageButton) findViewById(R.id.imageButtonprev);
        btnStop        = (ImageButton) findViewById(R.id.imageButtonstop);
        imgHinh        = (ImageView) findViewById(R.id.imageViewDisc);

    }
}
