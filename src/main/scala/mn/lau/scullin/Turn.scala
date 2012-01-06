package mn.lau.scullin

import player.Player

class Turn(val player: Player, val phase: Phase.Value = Phase.Action) {

  def nextPhase: Turn = {
    val nextPhase = if (phase == Phase.Action) {
                      Phase.Buy
                    } else if (phase == Phase.Buy) {
                      Phase.CleanUp
                    } else {
                      phase
                    }
    
    new Turn(player, nextPhase)
  }
}

object Phase extends Enumeration {
  type Phase = Value
  val Action, Buy, CleanUp = Value
}