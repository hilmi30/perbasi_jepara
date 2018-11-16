package com.perusdajepara.perbasijepara.video

import com.perusdajepara.perbasijepara.basecontract.BasePresenter

class VideoPresenter: BasePresenter<VideoView> {

    var mView: VideoView? = null

    override fun onAttach(view: VideoView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}