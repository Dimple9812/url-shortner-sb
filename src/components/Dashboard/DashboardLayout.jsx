// import React from "react";
// import Graph from "./Graph";
// import { dummyData } from "../../dummyData/data";
// import { useStoreContext } from "../../contextApi/ContextApi";
// import useFetchTotalClicks from "../../hooks/useQuery";

// // const DashboardLayout = () => {
// //   const refetch ={fasle}
// //   const { token } = useStoreContext();
// //   const [shortenPopUp, setShortenPopUp] = useState(false);

//   const {
//     isLoading,
//     data: totalClicks,
//     error,
//   } = useFetchTotalClicks(token);

//   // 🔍 DEBUG (remove later)
//   console.log("TOKEN:", token);
//   console.log("TOTAL CLICKS:", totalClicks);

//   return (
//     <div className="lg:px-14 sm:px-8 px-4 min-h-[calc(100vh-64px)]">
      
//       {isLoading ? (
//         <p>Loading.....</p>
//       ) : error ? (
//         <p className="text-red-500">Error loading data</p>
//       ) : (
//         <div className="lg:w-[90%] w-full mx-auto py-16">

//           {/* Graph */}
//           <div className="h-96 border rounded-md p-4">
//             <Graph
//               graphData={
//                 totalClicks && totalClicks.length > 0
//                   ? totalClicks
//                   : dummyData
//               }
//             />
//           </div>

//           {/* Button */}
//           <div className="py-5 sm:text-end text-center">
//             <button 
//                 className="bg-custom-gradient px-4 py-2 rounded-md text-white"
//                 onClick={() => setShortenPopUp(true)}>
//               Create a New Short URL
//             </button>
//           </div>
//         </div>
//       )}
//       <ShortenPopUp
//       refetch={refetch}
//       open={shortenPopUp}
//       setOpen={setShortenPopUp}
//       />
//     </div>
//   )
// }

// export default DashboardLayout;

// import React, { useState } from 'react'
// import Graph from './Graph'
// import { dummyData } from '../../dummyData/data'
// import { useStoreContext } from '../../contextApi/ContextApi'
// import {  useFetchMyShortUrls, useFetchTotalClicks } from '../../hooks/useQuery'
// import ShortenPopUp from './ShortenPopUp'
// import { FaLink } from 'react-icons/fa'
// import ShortenUrlList from './ShortenUrlList'
// import { useNavigate } from 'react-router-dom'
// //import Loader from '../Loader'

// const DashboardLayout = () => {
//     //const refetch = false;
//     const { token } = useStoreContext();
//     const [shortenPopUp, setShortenPopUp] = useState(false);

//     // console.log(useFetchTotalClicks(token, onError));

//     const {isLoading, data: myShortenUrls, refetch } = useFetchMyShortUrls(token, onError)
    
//     const {isLoading: loader, data: totalClicks} = useFetchTotalClicks(token, onError)

//     function onError() {
//       console.log("ERROR");
//     }

//   return (
//     <div className="lg:px-14 sm:px-8 px-4 min-h-[calc(100vh-64px)]">
//         {loader ? ( 
//             <Loader />
//         ): ( 
//         <div className="lg:w-[90%] w-full mx-auto py-16">
//             <div className=" h-96 relative ">
//                 {totalClicks.length === 0 && (
//                      <div className="absolute flex flex-col  justify-center sm:items-center items-end  w-full left-0 top-0 bottom-0 right-0 m-auto">
//                      <h1 className=" text-slate-800 font-serif sm:text-2xl text-[18px] font-bold mb-1">
//                        No Data For This Time Period
//                      </h1>
//                      <h3 className="sm:w-96 w-[90%] sm:ml-0 pl-6 text-center sm:text-lg text-sm text-slate-600 ">
//                        Share your short link to view where your engagements are
//                        coming from
//                      </h3>
//                    </div>
//                 )}
//                 <Graph graphData={totalClicks} />
//             </div>
//             <div className='py-5 sm:text-end text-center'>
//                 <button
//                     className='bg-custom-gradient px-4 py-2 rounded-md text-white'
//                     onClick={() => setShortenPopUp(true)}>
//                     Create a New Short URL
//                 </button>
//             </div>

//             <div>
//               {!isLoading && myShortenUrls.length === 0 ? (
//                 <div className="flex justify-center pt-16">
//                   <div className="flex gap-2 items-center justify-center  py-6 sm:px-8 px-5 rounded-md   shadow-lg  bg-gray-50">
//                     <h1 className="text-slate-800 font-montserrat   sm:text-[18px] text-[14px] font-semibold mb-1 ">
//                       You haven't created any short link yet
//                     </h1>
//                     <FaLink className="text-blue-500 sm:text-xl text-sm " />
//                   </div>
//               </div>
//               ) : (
//                   <ShortenUrlList data={myShortenUrls} />
//               )}
//             </div>
//         </div>
//         )}

//         <ShortenPopUp
//           refetch={refetch}
//           open={shortenPopUp}
//           setOpen={setShortenPopUp}
//         />
//     </div>
//   )
// }

// export default DashboardLayout

import React, { useState } from "react";
import Graph from "./Graph";
import { dummyData } from "../../dummyData/data";
import { useStoreContext } from "../../contextApi/ContextApi";
import {
  useFetchMyShortUrls,
  useFetchTotalClicks,
} from "../../hooks/useQuery";
import ShortenPopUp from "./ShortenPopUp";
import { FaLink } from "react-icons/fa";
import ShortenUrlList from "./ShortenUrlList";
import Loader from "../Loader";

const DashboardLayout = () => {
  const { token } = useStoreContext();
  const [shortenPopUp, setShortenPopUp] = useState(false);

  function onError(err) {
    console.log("ERROR:", err);
  }

  const {
    isLoading,
    data: myShortenUrls = [],
    refetch,
  } = useFetchMyShortUrls(token, onError);

  const {
    isLoading: loader,
    data: totalClicks = [],
  } = useFetchTotalClicks(token, onError);

  return (
    <div className="lg:px-14 sm:px-8 px-4 min-h-[calc(100vh-64px)]">
      {loader ? (
        <Loader />
      ) : (
        <div className="lg:w-[90%] w-full mx-auto py-16">
          
          {/* GRAPH */}
          <div className="h-96 relative border rounded-md p-4">
            {totalClicks.length === 0 ? (
              <div className="absolute flex flex-col justify-center items-center w-full h-full">
                <h1 className="text-slate-800 font-serif text-xl font-bold">
                  No Data For This Time Period
                </h1>
                <p className="text-slate-600 text-sm text-center">
                  Share your short link to see engagement stats 🚀
                </p>
              </div>
            ) : (
              <Graph graphData={totalClicks || dummyData} />
            )}
          </div>

          {/* BUTTON */}
          <div className="py-5 sm:text-end text-center">
            <button
              className="bg-custom-gradient px-4 py-2 rounded-md text-white"
              onClick={() => setShortenPopUp(true)}
            >
              Create a New Short URL
            </button>
          </div>

          {/* URL LIST */}
          <div>
            {!isLoading && myShortenUrls.length === 0 ? (
              <div className="flex justify-center pt-16">
                <div className="flex gap-2 items-center py-6 px-5 rounded-md shadow-lg bg-gray-50">
                  <h1 className="text-slate-800 text-sm font-semibold">
                    You haven't created any short link yet
                  </h1>
                  <FaLink className="text-blue-500" />
                </div>
              </div>
            ) : (
              <ShortenUrlList data={myShortenUrls} />
            )}
          </div>
        </div>
      )}

      <ShortenPopUp
        refetch={refetch}
        open={shortenPopUp}
        setOpen={setShortenPopUp}
      />
    </div>
  );
};

export default DashboardLayout;