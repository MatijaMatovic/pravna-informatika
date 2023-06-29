import "./verdictFeatures.css";

interface VerdictFeaturesParams {
  description: CaseDescription
}

export default function VerdictFeatures({ description }: VerdictFeaturesParams) {
  return (
    <div className="h-100">
      <div className="features-title">Detalji slučaja</div>
      <div className="overflow-auto h-100">
        {description &&
          Object.entries(description)
            .filter(([_, value]) => value)
            .map(([key, value]) => (
              <div key={key}>
                <span className="feature-key">{key}</span>{" "}
                <span className="feature-value">{value}</span>
              </div>
            ))}
      </div>
    </div>
  );
}
