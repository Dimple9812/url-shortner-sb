// import React from 'react'
// import ShortenItem from './ShortenItem'

// const ShortenUrlList = ({ data }) => {
//   return (
//     <div className='my-6 space-y-4'>
//         {data.map((item) => (
//             <ShortenItem key={item.id} {...item} />
//         ))}
//     </div>
//   )
// }

// export default ShortenUrlList

import React from "react";
import ShortenItem from "./ShortenItem";

const ShortenUrlList = ({ data }) => {
  // ✅ Always ensure array
  const safeData = Array.isArray(data) ? data : [];

  if (safeData.length === 0) {
    return (
      <div className="my-6 text-center text-gray-500">
        No short URLs found
      </div>
    );
  }

  return (
    <div className="my-6 space-y-4">
      {safeData.map((item, index) => (
        <ShortenItem key={item?.id || index} {...item} />
      ))}
    </div>
  );
};

export default ShortenUrlList;