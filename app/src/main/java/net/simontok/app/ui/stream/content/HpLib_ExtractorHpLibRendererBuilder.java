package net.simontok.app.ui.stream.content;

import android.content.Context;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;

class HpLib_ExtractorHpLibRendererBuilder implements HpLib_RendererBuilder {

    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;

    private final Context context;
    private final String userAgent;
    private final Uri uri;

    public HpLib_ExtractorHpLibRendererBuilder(Context context, String userAgent, Uri uri) {
        this.context = context;
        this.userAgent = userAgent;
        this.uri = uri;
    }

    @Override
    public void buildRenderers(ContentStreamActivity player) {
        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
        Handler mainHandler = player.getMainHandler();

        // Build the video and audio renderers.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(mainHandler, null);
        DataSource dataSource = new DefaultUriDataSource(context, bandwidthMeter, userAgent);
        ExtractorSampleSource sampleSource = new ExtractorSampleSource(uri, dataSource, allocator,
                BUFFER_SEGMENT_COUNT * BUFFER_SEGMENT_SIZE);
        MediaCodecVideoTrackRenderer videoRenderer = new MediaCodecVideoTrackRenderer(context,
                sampleSource, MediaCodecSelector.DEFAULT, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT, 5000);

        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource,MediaCodecSelector.DEFAULT,null,true);
        // Invoke the callback.
        TrackRenderer[] renderers = new TrackRenderer[ContentStreamActivity.RENDERER_COUNT];
        renderers[ContentStreamActivity.TYPE_VIDEO] = videoRenderer;
        renderers[ContentStreamActivity.TYPE_AUDIO] = audioRenderer;
        player.onRenderers(renderers, bandwidthMeter);

//        mainHandler.postAtTime(new Runnable() {
//            @Override
//            public void run() {
//                player.killPlayer();
//                AppLogger.e("postAtTime");
//            }
//        }, 1000000);
//        if (renderers.length==10000) {
//            AppLogger.e("b");
//        }
//        if (renderers.length==100000) {
//            AppLogger.e("c");
//        }
//        if (renderers.length==1000000) {
//            AppLogger.e("d");
//        }
//        if (renderers.length==10000000) {
//            AppLogger.e("e");
//        }
    }

    @Override
    public void cancel() {
        // Do nothing.
    }

}
