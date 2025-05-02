import React from 'react';
import { Cell } from './game';

interface Props {
  cell: Cell
}

class BoardCell extends React.Component<Props> {
  render(): React.ReactNode {
    const { text, playable, winner } = this.props.cell;
    let className = 'cell';

    if (text === 'X') {
      className += ' cell-x';
    } else if (text === 'O') {
      className += ' cell-o';
    } else if (playable) {
      className += ' playable';
    }

    if (winner) {
      className += ' winner';
    }

    return (
      <div className={className}>{text}</div>
    );
  }
}

export default BoardCell;