package ru.otus.livescore.stubs

import ru.otus.otuskotlin.livescore.common.models.LsMatch
import ru.otus.livescore.stubs.LsMatchStubSample.LS_MATCH1
import ru.otus.otuskotlin.livescore.common.models.LsMatchId

object LsMatchStub {
    fun get(): LsMatch = LS_MATCH1.copy()

    //fun prepareResult(block: LsMatch.() -> Unit): LsMatch = get().apply(block)

/*    fun prepareSearchList(filter: String, type: MkplDealSide) = listOf(
        mkplAdDemand("d-666-01", filter, type),
        mkplAdDemand("d-666-02", filter, type),
        mkplAdDemand("d-666-03", filter, type),
        mkplAdDemand("d-666-04", filter, type),
        mkplAdDemand("d-666-05", filter, type),
        mkplAdDemand("d-666-06", filter, type),
    )*/

    fun prepareMatchesList() = listOf(
        lsAddMatch("s-666-01", "Bykov", "Goncharov"),
        lsAddMatch("s-666-02", "Titov", "Mamontov"),
        lsAddMatch("s-666-03", "Petrov", "Vodkin"),
        lsAddMatch("s-666-04", "Tupolev", "Yakovlev"),
        lsAddMatch("s-666-05", "Trukhin", "Gerasimov"),
        lsAddMatch("s-666-06", "Popov", "Markoni"),
    )

//    private fun mkplAdDemand(id: String, filter: String, type: MkplDealSide) =
//        mkplAd(AD_DEMAND_BOLT1, id = id, filter = filter, type = type)

    private fun lsAddMatch(id: String, part1: String, part2: String) =
        lsMatch(LS_MATCH1, id = id, part1 = part1, part2 = part2)

    private fun lsMatch(base: LsMatch, id: String, part1: String, part2: String) = base.copy(
        id = LsMatchId(id),
        participant1 = part1,
        participant2 = part2,
    )

}
