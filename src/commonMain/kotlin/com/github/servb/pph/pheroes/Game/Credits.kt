package com.github.servb.pph.pheroes.Game

import com.github.servb.pph.gxlib.IiDib
import com.github.servb.pph.gxlib.copyTo
import com.github.servb.pph.gxlib.iDib
import com.github.servb.pph.util.ISizeInt
import com.github.servb.pph.util.SizeT
import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.bitmap.context2d
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.IPointInt
import com.soywiz.korma.geom.IRectangleInt
import com.soywiz.korma.geom.PointInt
import com.soywiz.korma.geom.SizeInt
import kotlin.properties.Delegates

// InitMotionBlur, InitBump, InitLight and related are deleted as unused in sources
class iCreditsComposer {

    private val m_back: iDib = iDib()
    private var m_pos: SizeT by Delegates.notNull()
    private val m_logo: iDib = iDib()
    private val buffer = Bitmap32(320, 240)  // todo: port blitting and get rid of this buffer

    private var m_bEnd: Boolean = false
    private var m_scrPos: Int = 0
    private var m_bShowCredits: Boolean
    private var t: Double by Delegates.notNull()
    private var dt: Double by Delegates.notNull()

    constructor() {
        m_bShowCredits = false
    }

    suspend fun Init() {
        m_back.Init(ISizeInt(320, 720), IiDib.Type.RGB)
        resourcesVfs["pheroes/bin/Resources/hmm/GFX/Pix/MenuBack.png"].readBitmap().copyTo(m_back)

        val title = resourcesVfs["pheroes/bin/Resources/hmm/GFX/Pix/title_hmm.png"].readBitmap()
        m_logo.Init(SizeInt(title.size), IiDib.Type.RGBA)
        title.copyTo(m_logo)

        m_pos = 0
        t = 0.0
        dt = 0.03  // adjust this if too fast or slow...
    }

    fun Compose(surface: iDib) {
        val xval = ((m_pos++) / 2) % 720

        if (xval < 240) {
            m_back.CopyRectToDibXY(surface, IRectangleInt(0, 720 - xval, 320, xval), PointInt())
            m_back.CopyRectToDibXY(surface, IRectangleInt(0, 0, 320, 240 - xval), IPointInt(0, xval))
        } else {
            m_back.CopyRectToDibXY(surface, IRectangleInt(0, 720 - xval, 320, 240), PointInt())
        }

        t += dt

        //Compose credits
        ++m_scrPos
        val composed: SizeT = 0
        if (m_bShowCredits) {
            // todo: port code of text drawing
        }

        if (composed == 0) {
            m_bEnd
        }

        // todo: try to implement original blitting:
//        // compose 'Heroes' logo directly on surface  // todo: won't work on other resolutions
//        val src = m_logo.GetPtr()
//        val dst = surface.GetPtr()
//        // magic position constant ;)
//        dst.incrementOffset(320 * 4 + 45)
//        repeat(m_logo.GetHeight()) { nh ->
//            src.incrementOffset(blit_16aline(src, dst, m_logo.GetWidth() / 2))
//            dst.incrementOffset(320)
//        }
        surface.backingBitmap.copy(0, 0, buffer, 0, 0, buffer.width, buffer.height)
        buffer.context2d {
            drawImage(m_logo.backingBitmap, 45, 4)  // magic position constant ;)
        }
        buffer.copy(0, 0, surface.backingBitmap, 0, 0, buffer.width, buffer.height)
    }

    fun StartCredits() {
        check(!m_bShowCredits)
        m_bEnd = false
        m_bShowCredits = true
        m_scrPos = 15
    }

    fun StopCredits() {
        check(m_bShowCredits)
        m_bShowCredits = false
    }

    fun IsCreaditsStarted(): Boolean = m_bShowCredits

    fun IsCreaditsEnd(): Boolean = m_bEnd
}
