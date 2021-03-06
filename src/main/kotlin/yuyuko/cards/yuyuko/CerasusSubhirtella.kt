package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.CerasusSubhirtellaPower

class CerasusSubhirtella : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Cerasus Subhirtella"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 0
        val HEAL_AMOUNT = 1
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = HEAL_AMOUNT
        this.magicNumber = HEAL_AMOUNT
        this.exhaust = true
        this.retain = true
    }

    override fun makeCopy(): AbstractCard = CerasusSubhirtella()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                HealAction(self, self, this.magicNumber)
        )
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, 1, false)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        CerasusSubhirtellaPower(),
                        1
                )
        )
    }


    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
        upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
    }

    override fun upgradeName() {
        ++this.timesUpgraded
        this.upgraded = true
        this.name = "$NAME+$timesUpgraded"
        this.initializeTitle()
    }

}