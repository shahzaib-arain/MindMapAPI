import { Chip } from '@mui/material';
import { SentimentVerySatisfied, SentimentSatisfied, SentimentNeutral, SentimentDissatisfied, SentimentVeryDissatisfied } from '@mui/icons-material';

const SentimentDisplay = ({ sentiment }) => {
  const getSentimentProps = () => {
    switch (sentiment) {
      case 'HAPPY':
        return {
          label: 'Happy',
          icon: <SentimentVerySatisfied />,
          color: 'success',
        };
      case 'SAD':
        return {
          label: 'Sad',
          icon: <SentimentVeryDissatisfied />,
          color: 'error',
        };
      case 'NEUTRAL':
        return {
          label: 'Neutral',
          icon: <SentimentNeutral />,
          color: 'warning',
        };
      case 'ANGRY':
        return {
          label: 'Angry',
          icon: <SentimentDissatisfied />,
          color: 'error',
        };
      case 'EXCITED':
        return {
          label: 'Excited',
          icon: <SentimentSatisfied />,
          color: 'success',
        };
      default:
        return {
          label: sentiment,
          icon: <SentimentNeutral />,
          color: 'default',
        };
    }
  };

  const { label, icon, color } = getSentimentProps();

  return (
    <Chip
      icon={icon}
      label={label}
      color={color}
      size="small"
      variant="outlined"
    />
  );
};

export default SentimentDisplay;